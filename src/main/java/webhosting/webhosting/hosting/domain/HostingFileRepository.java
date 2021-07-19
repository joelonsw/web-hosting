package webhosting.webhosting.hosting.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import webhosting.webhosting.login.domain.User;

import java.util.List;

public interface HostingFileRepository extends JpaRepository<HostingFile, Long> {
    void deleteAllByUser(User user);
    List<HostingFile> findByUser(User user);
    List<HostingFile> findByUserAndFileType(User user, FileType fileType);
    HostingFile findByUserAndFilePath(User user, String filePath);
}
