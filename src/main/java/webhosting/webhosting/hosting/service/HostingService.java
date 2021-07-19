package webhosting.webhosting.hosting.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.hosting.domain.FileType;
import webhosting.webhosting.hosting.domain.HostingFile;
import webhosting.webhosting.hosting.domain.HostingFileRepository;
import webhosting.webhosting.login.domain.User;
import webhosting.webhosting.login.domain.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HostingService {
    @Value("${hosting.file.path}")
    private String filePath;

    @Value("${hosting.server.path}")
    private String serverPath;

    private HostingFileRepository hostingFileRepository;
    private UserRepository userRepository;

    public HostingService(HostingFileRepository hostingFileRepository, UserRepository userRepository) {
        this.hostingFileRepository = hostingFileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String saveFile(User user, MultipartFile htmlFile,
                           List<MultipartFile> cssFiles, List<MultipartFile> jsFiles) {
        makeUserFolder(user);
        saveFile(user, htmlFile, FileType.HTML);
        cssFiles.forEach(cssFile -> saveFile(user, cssFile, FileType.CSS));
        jsFiles.forEach(jsFile -> saveFile(user, jsFile, FileType.JS));
        return serverPath + user.getName();
    }

    private void makeUserFolder(User user) {
        File Folder = new File(filePath + user.getName());
        if (Folder.exists()) {
            Folder.delete();
            Folder.mkdir();
            hostingFileRepository.deleteAllByUser(user);
            return;
        }
        Folder.mkdir();
    }

    public void saveFile(User user, MultipartFile file, FileType fileType) {
        final String originalFilename = file.getOriginalFilename();
        if (originalFilename.length() == 0) {
            return;
        }

        final String userFilePath = generateFilePath(user, originalFilename);
        final File fileToSaveInLocalPC = new File(userFilePath);
        try {
            file.transferTo(fileToSaveInLocalPC);
            hostingFileRepository.save(new HostingFile(user, userFilePath, fileType));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.");
        }
    }

    private String generateFilePath(User user, String originalFilename) {
        return filePath + user.getName() + "/" + originalFilename;
    }

    public String getUserHtmlFile(String userName) {
        final User user = userRepository.findByName(userName);
        final HostingFile hostingFile = hostingFileRepository.findByUserAndFileType(user, FileType.HTML).get(0);
        final File htmlFile = new File(hostingFile.getFilePath());
        try {
            final Document htmlDocument = Jsoup.parse(htmlFile, "UTF-8");
            appendCssTag(userName, htmlDocument);
            appendJsTag(userName, htmlDocument);
            appendWaterMark(htmlDocument);
            return htmlDocument.outerHtml();
        } catch (IOException e) {
            throw new RuntimeException("파일 조회에 실패했습니다.");
        }
    }

    private void appendCssTag(String userName, Document htmlDocument) {
        final User user = userRepository.findByName(userName);
        final List<HostingFile> cssFiles = hostingFileRepository.findByUserAndFileType(user, FileType.CSS);
        for (HostingFile cssFile : cssFiles) {
            String serverCssPath = generateServerPath(cssFile.getFilePath());
            String CSSHTML = "<link rel=\"stylesheet\" href=\"" + serverCssPath + "\">";
            htmlDocument.selectFirst("head").child(0).before(CSSHTML);
        }
    }

    private void appendJsTag(String userName, Document htmlDocument) {
        final User user = userRepository.findByName(userName);
        final List<HostingFile> jsFiles = hostingFileRepository.findByUserAndFileType(user, FileType.CSS);
        for (HostingFile jsFile : jsFiles) {
            String serverJsPath = generateServerPath(jsFile.getFilePath());
            String JSHTML = "<script src=\"" + serverJsPath + "\"" + "type=\"module\">";
            htmlDocument.selectFirst("body").child(0).before(JSHTML);
            String JSHTMLwithoutModule = "<script src=\"" + serverJsPath + "\"" + ">";
            htmlDocument.selectFirst("body").child(0).before(JSHTMLwithoutModule);
        }
    }

    private String generateServerPath(String filePath) {
        return serverPath + filePath.substring(filePath.length());
    }

    private void appendWaterMark(Document htmlDocument) {
        final String waterMark =
                "<div style=\"position: fixed; bottom:0; width: 100%; margin: 15px;\">\n" +
                        "    <h5>Powered By <a href=\"https://joel-web-hosting.o-r.kr/\">Joel Web Hosting</a></h5>\n" +
                        "</div>";
        htmlDocument.selectFirst("body").child(0).before(waterMark);
    }

    public String getUserResource(String userName, String resource) {
        final User user = userRepository.findByName(userName);
        final HostingFile hostingFile = hostingFileRepository.findByUserAndFilePath(user, generateFilePath(user, resource));
        try {
            final Stream<String> lines = Files.lines(Paths.get(hostingFile.getFilePath()));
            return lines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("파일 조회에 실패했습니다.");
        }
    }
}
