package webhosting.webhosting.hosting.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HostingFileRepository extends JpaRepository<HostingFile, Long> {

    boolean existsByPageName(String pageName);

    Optional<List<HostingFile>> findByPageName(String pageName);

    Optional<HostingFile> findByPageNameAndServerPath(String pageName, String serverPath);

    @Query("SELECT DISTINCT file.pageName FROM HostingFile as file")
    List<String> findAllPageNames();
}
