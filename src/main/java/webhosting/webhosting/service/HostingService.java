package webhosting.webhosting.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.dao.HostingDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HostingService {
    private HostingDao hostingDao;

    private static String FILE_PATH = "/home/ubuntu/userfile/";
    private static String SERVER_PATH = "http://52.79.235.230/pages/";

    public HostingService(HostingDao hostingDao) {
        this.hostingDao = hostingDao;
    }

    @Transactional
    public String saveFile(String userId, MultipartFile htmlFile,
                         List<MultipartFile> cssFiles, List<MultipartFile> jsFiles) {
        makeUserFileFolder(userId);
        saveSingleFile(userId, htmlFile, "html");
        cssFiles.forEach(cssFile -> saveSingleFile(userId, cssFile, "css"));
        jsFiles.forEach(jsFile -> saveSingleFile(userId, jsFile, "js"));
        return SERVER_PATH + userId;
    }

    private void makeUserFileFolder(String userId) {
        String userFileFolderPath = FILE_PATH + userId;
        File Folder = new File(userFileFolderPath);

        if (Folder.exists()) {
            Folder.delete();
            Folder.mkdir();
            return;
        }

        Folder.mkdir();
    }

    public void saveSingleFile(String userId, MultipartFile file, String fileType) {
        final String originalFilename = file.getOriginalFilename();
        final String filePath = FILE_PATH + userId + "/" + originalFilename;
        final File fileToSaveInLocalPC = new File(filePath);
        try {
            file.transferTo(fileToSaveInLocalPC);
            hostingDao.saveFile(userId, filePath, fileType);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.");
        }
    }

    public String getUserHtmlFile(String userId) {
        final String htmlFilePath = hostingDao.getHtmlFilePath(userId);
        final File htmlFile = new File(htmlFilePath);
        try {
            final Document htmlDocument = Jsoup.parse(htmlFile, "UTF-8");
            appendCssTag(userId, htmlDocument);
            appendJsTag(userId, htmlDocument);
            appendWaterMark(htmlDocument);
            return htmlDocument.outerHtml();
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.");
        }
    }

    private void appendCssTag(String userId, Document htmlDocument) {
        final List<String> cssFilePaths = hostingDao.getCssFilePath(userId);
        for (String cssFilePath : cssFilePaths) {
            String serverCssPath = SERVER_PATH + cssFilePath.substring(FILE_PATH.length());
            String CSSHTML = "<link rel=\"stylesheet\" href=\"" + serverCssPath + "\">";
            htmlDocument.selectFirst("head").child(0).before(CSSHTML);
        }
    }

    private void appendJsTag(String userId, Document htmlDocument) {
        final List<String> jsFilePaths = hostingDao.getJsFilePath(userId);
        for (String jsFilePath : jsFilePaths) {
            String serverJsPath = SERVER_PATH + jsFilePath.substring(FILE_PATH.length());
            String JSHTML = "<script src=\"" + serverJsPath + "\"" + "type=\"module\">";
            htmlDocument.selectFirst("body").child(0).before(JSHTML);
            String JSHTMLwithoutModule = "<script src=\"" + serverJsPath + "\"" + ">";
            htmlDocument.selectFirst("body").child(0).before(JSHTMLwithoutModule);
        }
    }

    private void appendWaterMark(Document htmlDocument) {
        String waterMark =
                "<div style=\"position: fixed; bottom:0; width: 100%; margin-left: 10px;\">\n" +
                "    <h5>Powered By <a href=\"http://52.79.235.230/\">Joel Web Hosting</a></h5>\n" +
                "</div>";
        htmlDocument.selectFirst("body").child(0).before(waterMark);
    }

    public String getUserResource(String userId, String resource) {
        final String filePath = hostingDao.getResource(userId, FILE_PATH + userId + "/" + resource);
        try {
            final Stream<String> lines = Files.lines(Paths.get(filePath));
            return lines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("파일 조회에 실패했습니다.");
        }
    }
}
