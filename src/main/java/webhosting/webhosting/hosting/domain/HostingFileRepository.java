package webhosting.webhosting.hosting.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import webhosting.webhosting.member.domain.User;

import java.util.List;
import java.util.Optional;

public interface HostingFileRepository extends JpaRepository<HostingFile, Long> {
    void deleteAllByUser(User user);
    Optional<List<HostingFile>> findByUser(User user);
    Optional<List<HostingFile>> findByUserAndFileType(User user, FileType fileType);
    Optional<HostingFile> findByUserAndFilePath(User user, String filePath);
}
