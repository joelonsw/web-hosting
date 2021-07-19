package webhosting.webhosting.hosting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import webhosting.webhosting.login.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class HostingFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;

    public HostingFile(User user, String filePath, FileType fileType) {
        this.user = user;
        this.filePath = filePath;
        this.fileType = fileType;
    }
}
