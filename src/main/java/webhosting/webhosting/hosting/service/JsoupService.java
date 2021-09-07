package webhosting.webhosting.hosting.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import webhosting.webhosting.hosting.domain.HostingFile;
import webhosting.webhosting.hosting.domain.HostingFiles;
import webhosting.webhosting.hosting.exception.FileReadException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static webhosting.webhosting.hosting.service.util.FileNameConverter.generateCssUrlPath;
import static webhosting.webhosting.hosting.service.util.FileNameConverter.generateJsUrlPath;

@Service
public class JsoupService {

    public String manipulateHtml(HostingFiles hostingFiles) {
        final HostingFile htmlFile = hostingFiles.findHtmlFile();
        final List<HostingFile> cssFiles = hostingFiles.findCssFiles();
        final List<HostingFile> jsFiles = hostingFiles.findJsFiles();
        return manipulateHtml(htmlFile, cssFiles, jsFiles);
    }

    private String manipulateHtml(HostingFile htmlFile, List<HostingFile> cssFiles, List<HostingFile> jsFiles) {
        final File html = new File(htmlFile.getServerPath());
        try {
            final Document htmlDocument = Jsoup.parse(html, "UTF-8");
            appendCssTag(cssFiles, htmlDocument);
            appendJsTag(jsFiles, htmlDocument);
            appendWaterMark(htmlDocument);
            return htmlDocument.outerHtml();
        } catch (IOException e) {
            throw new FileReadException("파일 조회에 실패했습니다.");
        }
    }

    private void appendCssTag(List<HostingFile> cssFiles, Document htmlDocument) {
        for (HostingFile cssFile : cssFiles) {
            String urlCssPath = generateCssUrlPath(cssFile.getServerPath());
            String cssTagToHtml = "<link rel=\"stylesheet\" href=\"" + urlCssPath + "\">";
            htmlDocument.selectFirst("head").child(0).before(cssTagToHtml);
        }
    }

    private void appendJsTag(List<HostingFile> jsFiles, Document htmlDocument) {
        for (HostingFile jsFile : jsFiles) {
            String urlJsPath = generateJsUrlPath(jsFile.getServerPath());
            final Element body = htmlDocument.selectFirst("body");
            body.appendElement("script").attr("src", urlJsPath).attr("type", "module");
            body.appendElement("script").attr("src", urlJsPath);
        }
    }

    private void appendWaterMark(Document htmlDocument) {
        final String waterMark =
                "<div style=\"position: fixed; bottom:0; width: 100%; margin: 15px;\">\n" +
                        "    <h5>Powered By <a href=\"https://easy-deploy.kr/\">Easy Deploy</a></h5>\n" +
                        "</div>";
        htmlDocument.selectFirst("body").child(0).before(waterMark);
    }
}
