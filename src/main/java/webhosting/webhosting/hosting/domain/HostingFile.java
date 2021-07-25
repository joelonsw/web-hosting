package webhosting.webhosting.hosting.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import webhosting.webhosting.member.domain.User;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class HostingFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_user")
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
