package webhosting.webhosting.hosting.service;

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

    private JsoupService jsoupService;
    private HostingFileRepository hostingFileRepository;
    private UserRepository userRepository;

    public HostingService(JsoupService jsoupService, HostingFileRepository hostingFileRepository, UserRepository userRepository) {
        this.jsoupService = jsoupService;
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

    private void saveFile(User user, MultipartFile file, FileType fileType) {
        final String originalFilename = file.getOriginalFilename();
        if (originalFilename.length() == 0) {
            return;
        }

        final String userFilePath = generateFilePath(user, originalFilename);
        final File localFile = new File(userFilePath);
        try {
            file.transferTo(localFile);
            hostingFileRepository.save(new HostingFile(user, userFilePath, fileType));
        } catch (IOException e) {
            throw new RuntimeException(fileType.name() + " 파일 저장에 실패했습니다.");
        }
    }

    private String generateFilePath(User user, String originalFilename) {
        return filePath + user.getName() + "/" + originalFilename;
    }

    public String getUserHtmlFile(String userName) {
        final User user = userRepository.findByName(userName);
        final HostingFile htmlFile = hostingFileRepository.findByUserAndFileType(user, FileType.HTML).get(0);
        final List<HostingFile> cssFiles = hostingFileRepository.findByUserAndFileType(user, FileType.CSS);
        final List<HostingFile> jsFiles = hostingFileRepository.findByUserAndFileType(user, FileType.JS);
        return jsoupService.manipulateHtml(htmlFile, cssFiles, jsFiles);
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
