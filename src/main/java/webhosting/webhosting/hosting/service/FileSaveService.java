package webhosting.webhosting.hosting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.hosting.domain.FileType;
import webhosting.webhosting.hosting.domain.HostingFile;
import webhosting.webhosting.hosting.domain.HostingFileRepository;
import webhosting.webhosting.hosting.exception.FileSaveException;
import webhosting.webhosting.hosting.exception.FolderExistException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static webhosting.webhosting.hosting.service.util.FileNameConverter.generatePageDirectory;
import static webhosting.webhosting.hosting.service.util.FileNameConverter.generateServerFilePath;

@Service
public class FileSaveService {

    private final HostingFileRepository hostingFileRepository;

    public FileSaveService(HostingFileRepository hostingFileRepository) {
        this.hostingFileRepository = hostingFileRepository;
    }

    @Transactional
    public void saveFile(String pageName, MultipartFile htmlFile, List<MultipartFile> cssFiles, List<MultipartFile> jsFiles) {
        makePageDirectory(pageName);
        saveHtmlFile(pageName, htmlFile);
        saveCssFiles(pageName, cssFiles);
        saveJsFiles(pageName, jsFiles);
    }

    private void makePageDirectory(String pageName) {
        String pageDirectory = generatePageDirectory(pageName);
        File directory = new File(pageDirectory);
        if (directory.exists()) {
            throw new FolderExistException("이미 존재하는 페이지 입니다");
        }
        directory.mkdir();
    }

    private void saveHtmlFile(String pageName, MultipartFile htmlFile) {
        saveFile(pageName, htmlFile, FileType.HTML);
    }

    private void saveCssFiles(String pageName, List<MultipartFile> cssFiles) {
        if (Objects.isNull(cssFiles)) {
            return;
        }
        cssFiles.forEach(cssFile -> saveFile(pageName, cssFile, FileType.CSS));
    }

    private void saveJsFiles(String pageName, List<MultipartFile> jsFiles) {
        if (Objects.isNull(jsFiles)) {
            return;
        }
        jsFiles.forEach(jsFile -> saveFile(pageName, jsFile, FileType.JS));
    }

    private void saveFile(String pageName, MultipartFile file, FileType fileType) {
        final String originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename) || originalFilename.isEmpty()) {
            return;
        }
        saveFileToServerAndDb(pageName, file, fileType, originalFilename);
    }

    private void saveFileToServerAndDb(String pageName, MultipartFile file, FileType fileType, String originalFilename) {
        final String serverPath = generateServerFilePath(pageName, originalFilename);
        final File localFile = new File(serverPath);
        try {
            file.transferTo(localFile);
            hostingFileRepository.save(new HostingFile(pageName, serverPath, fileType));
        } catch (IOException e) {
            throw new FileSaveException(fileType.name() + " 파일 저장에 실패했습니다.");
        }
    }
}
