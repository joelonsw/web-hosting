package webhosting.webhosting.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import webhosting.webhosting.hosting.domain.FileType;
import webhosting.webhosting.hosting.domain.HostingFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
    private String socialId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HostingFile> hostingFiles = new ArrayList<>();

    public User(String name, String imageUrl, String socialId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.socialId = socialId;
    }

    public HostingFile findHostingHTML() {
        return hostingFiles.stream()
                .filter(HostingFile::isHTML)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("HTML file not found"));
    }

    public void addHostingFile(HostingFile hostingFile) {
        this.hostingFiles.add(hostingFile);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
