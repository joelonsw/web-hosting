package webhosting.webhosting.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.dao.HostingDao;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HostingService {
    private HostingDao hostingDao;

    private static String FILE_PATH = "C:\\Users\\joel6\\Desktop\\";
    private static String SERVER_PATH = "http://localhost:8080/page/";

    public HostingService(HostingDao hostingDao) {
        this.hostingDao = hostingDao;
    }

    @Transactional
    public void saveFile(String userId, MultipartFile htmlFile,
                         List<MultipartFile> cssFiles, List<MultipartFile> jsFiles) throws IOException {

        saveSingleFile(userId, htmlFile, "html");

        for (MultipartFile cssFile : cssFiles) {
            saveSingleFile(userId, cssFile, "css");
        }

        for (MultipartFile jsFile : jsFiles) {
            saveSingleFile(userId, jsFile, "js");
        }
    }

    public void saveSingleFile(String userId, MultipartFile file, String fileType) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        final String filePath = FILE_PATH + originalFilename;
        final File fileToSaveInLocalPC = new File(filePath);
        file.transferTo(fileToSaveInLocalPC);
        hostingDao.saveFile(userId, filePath, fileType);
    }

    public String getUserHtmlFile(String userId) throws IOException {
        final String htmlFilePath = hostingDao.getHtmlFilePath(userId);
        final File htmlFile = new File(htmlFilePath);
        final Document htmlDocument = Jsoup.parse(htmlFile, "UTF-8");

        final List<String> cssFilePaths = hostingDao.getCssFilePath(userId);
        for (String cssFilePath : cssFilePaths) {
            String serverCssPath = SERVER_PATH + userId +  "/" + cssFilePath.substring(FILE_PATH.length());
            String html = "<link rel=\"stylesheet\" href=\"" + serverCssPath + "\">";
            htmlDocument.selectFirst("head").child(0).before(html);
        }

        final List<String> jsFilePaths = hostingDao.getJsFilePath(userId);
        for (String jsFilePath : jsFilePaths) {
            String serverJsPath = SERVER_PATH + userId +  "/" + jsFilePath.substring(FILE_PATH.length());
            String htmlWithModule = "<script src=\"" + serverJsPath + "\"" + "type=\"module\">";
            htmlDocument.selectFirst("body").child(0).before(htmlWithModule);
            String html = "<script src=\"" + serverJsPath + "\"" + ">";
            htmlDocument.selectFirst("body").child(0).before(html);
        }

        return htmlDocument.outerHtml();
    }

    public String getUserResource(String userId, String resource) throws IOException {
        final String filePath = hostingDao.getResource(userId, FILE_PATH + resource);
        final Stream<String> lines = Files.lines(Paths.get(filePath));
        final String fileAsString = lines.collect(Collectors.joining(System.lineSeparator()));
        return fileAsString;
    }
}
