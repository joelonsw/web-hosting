package webhosting.webhosting.hosting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.hosting.domain.HostingFile;
import webhosting.webhosting.hosting.domain.HostingFileRepository;
import webhosting.webhosting.hosting.domain.HostingFiles;
import webhosting.webhosting.hosting.exception.FileReadException;
import webhosting.webhosting.notification.service.NotificationService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static webhosting.webhosting.hosting.service.util.FileNameConverter.generateServerFilePath;

@Slf4j
@Service
public class HostingService {

    private final JsoupService jsoupService;
    private final FileSaveService fileSaveService;
    private final HostingFileRepository hostingFileRepository;
    private final NotificationService notificationService;

    public HostingService(JsoupService jsoupService, FileSaveService fileSaveService,
                          HostingFileRepository hostingFileRepository, NotificationService notificationService) {
        this.jsoupService = jsoupService;
        this.fileSaveService = fileSaveService;
        this.hostingFileRepository = hostingFileRepository;
        this.notificationService = notificationService;
    }

    public boolean checkPageUsable(String pageName) {
        final boolean exists = hostingFileRepository.existsByPageName(pageName);
        return !exists;
    }

    public void saveFile(String pageName, MultipartFile htmlFile, List<MultipartFile> cssFiles, List<MultipartFile> jsFiles) {
        fileSaveService.saveFile(pageName, htmlFile, cssFiles, jsFiles);
        notificationService.sendSlackDeployMessage("https://easy-deploy.kr/pages/" + pageName);
    }

    public String getPage(String pageName) {
        final List<HostingFile> hostingFiles = hostingFileRepository.findByPageName(pageName)
                .orElseThrow(FileReadException::new);
        return jsoupService.manipulateHtml(new HostingFiles(hostingFiles));
    }

    public String getResource(String pageName, String resource) {
        final String serverPath = generateServerFilePath(pageName, resource);
        final HostingFile hostingFile = hostingFileRepository.findByPageNameAndServerPath(pageName, serverPath)
                .orElseThrow(FileReadException::new);

        try (Stream<String> lines = Files.lines(Paths.get(hostingFile.getServerPath()), StandardCharsets.UTF_8)) {
            return lines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new FileReadException("파일 조회에 실패했습니다.");
        }
    }
}
