package webhosting.webhosting.hosting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class HostingFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageName;

    @Column(nullable = false)
    private String serverPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    public HostingFile(String pageName, String serverPath, FileType fileType) {
        this.pageName = pageName;
        this.serverPath = serverPath;
        this.fileType = fileType;
    }

    public boolean isHtml() {
        return fileType == FileType.HTML;
    }

    public boolean isCss() {
        return fileType == FileType.CSS;
    }

    public boolean isJs() {
        return fileType == FileType.JS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostingFile that = (HostingFile) o;
        return Objects.equals(pageName, that.pageName) &&
                Objects.equals(serverPath, that.serverPath) &&
                fileType == that.fileType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageName, serverPath, fileType);
    }
}
