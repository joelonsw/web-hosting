package webhosting.webhosting.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.dao.HostingDao;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class HostingService {
    private HostingDao hostingDao;

    private static String FILE_PATH = "C:\\Users\\joel6\\Desktop\\";

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

    public String getUserFile(String userId) throws IOException {
        final String htmlFilePath = hostingDao.getHtmlFilePath(userId);
        final File htmlFile = new File(htmlFilePath);
        final Document htmlDocument = Jsoup.parse(htmlFile, "UTF-8");
        return htmlDocument.outerHtml();
    }
}
