package webhosting.webhosting.hosting.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import webhosting.webhosting.hosting.domain.HostingFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsoupService {
    @Value("${hosting.file.path}")
    private String filePath;

    @Value("${hosting.server.path}")
    private String serverPath;

    public String manipulateHtml(HostingFile htmlFile, List<HostingFile> cssFiles, List<HostingFile> jsFiles) {
        final File HTML = new File(htmlFile.getFilePath());
        try {
            final Document htmlDocument = Jsoup.parse(HTML, "UTF-8");
            appendCssTag(cssFiles, htmlDocument);
            appendJsTag(jsFiles, htmlDocument);
            appendWaterMark(htmlDocument);
            return htmlDocument.outerHtml();
        } catch (IOException e) {
            throw new RuntimeException("파일 조회에 실패했습니다.");
        }
    }

    private void appendCssTag(List<HostingFile> cssFiles, Document htmlDocument) {
        for (HostingFile cssFile : cssFiles) {
            String serverCssPath = generateServerPath(cssFile.getFilePath());
            String CSSHTML = "<link rel=\"stylesheet\" href=\"" + serverCssPath + "\">";
            htmlDocument.selectFirst("head").child(0).before(CSSHTML);
        }
    }

    private void appendJsTag(List<HostingFile> jsFiles, Document htmlDocument) {
        for (HostingFile jsFile : jsFiles) {
            String serverJsPath = generateServerPath(jsFile.getFilePath());
            String JSHTML = "<script src=\"" + serverJsPath + "\"" + "type=\"module\">";
            htmlDocument.selectFirst("body").child(0).before(JSHTML);
            String JSHTMLwithoutModule = "<script src=\"" + serverJsPath + "\"" + ">";
            htmlDocument.selectFirst("body").child(0).before(JSHTMLwithoutModule);
        }
    }

    private String generateServerPath(String savedFilePath) {
        return serverPath + savedFilePath.substring(filePath.length());
    }

    private void appendWaterMark(Document htmlDocument) {
        final String waterMark =
                "<div style=\"position: fixed; bottom:0; width: 100%; margin: 15px;\">\n" +
                    "    <h5>Powered By <a href=\"https://joel-web-hosting.o-r.kr/\">Joel Web Hosting</a></h5>\n" +
                "</div>";
        htmlDocument.selectFirst("body").child(0).before(waterMark);
    }
}
