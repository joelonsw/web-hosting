package webhosting.webhosting.hosting.domain;

import webhosting.webhosting.hosting.exception.FileReadException;

import java.util.List;
import java.util.stream.Collectors;

public class HostingFiles {
    private final List<HostingFile> hostingFiles;

    public HostingFiles(List<HostingFile> hostingFiles) {
        this.hostingFiles = hostingFiles;
    }

    public HostingFile findHtmlFile() {
        return hostingFiles.stream()
                .filter(HostingFile::isHtml)
                .findAny()
                .orElseThrow(FileReadException::new);
    }

    public List<HostingFile> findCssFiles() {
        return hostingFiles.stream()
                .filter(HostingFile::isCss)
                .collect(Collectors.toList());
    }

    public List<HostingFile> findJsFiles() {
        return hostingFiles.stream()
                .filter(HostingFile::isJs)
                .collect(Collectors.toList());
    }
}
