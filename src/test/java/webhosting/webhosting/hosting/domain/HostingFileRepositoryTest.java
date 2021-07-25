package webhosting.webhosting.hosting.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.domain.UserRepository;
import webhosting.webhosting.login.exception.NotLoggedInException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HostingFileRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HostingFileRepository hostingFileRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        final User user = new User("user", "image", "socialId");
        userRepository.save(user);

        hostingFileRepository.save(new HostingFile(user, "HTMLFilePath", FileType.HTML));
        hostingFileRepository.save(new HostingFile(user, "CSSFilePath1", FileType.CSS));
        hostingFileRepository.save(new HostingFile(user, "CSSFilePath2", FileType.CSS));
        hostingFileRepository.save(new HostingFile(user, "JSFilePath1", FileType.JS));
        hostingFileRepository.save(new HostingFile(user, "JSFilePath2", FileType.JS));
        hostingFileRepository.save(new HostingFile(user, "JSFilePath3", FileType.JS));
        hostingFileRepository.save(new HostingFile(user, "JSFilePath4", FileType.JS));

        testEntityManager.clear();
    }

    @DisplayName("user가 업로드한 hostingFile들을 지울 수 있다.")
    @Test
    void deleteAllByUser() {
        final User user = userRepository.findByName("user")
                .orElseThrow(NotLoggedInException::new);
        hostingFileRepository.deleteAllByUser(user);

        final List<HostingFile> hostingFiles = hostingFileRepository.findByUser(user).orElseThrow(() -> new IllegalStateException());
        assertThat(hostingFiles).hasSize(0);
    }

    @DisplayName("user가 업로드한 파일을 User로 찾아올 수 있다.")
    @Test
    void findByUser() {
        final User user = userRepository.findByName("user")
                .orElseThrow(NotLoggedInException::new);
        final List<HostingFile> hostingFiles = hostingFileRepository.findByUser(user).orElseThrow(() -> new IllegalStateException());
        assertThat(hostingFiles).hasSize(7);
    }

    @DisplayName("user가 업로드한 파일을 User와 FileType으로 찾아올 수 있다.")
    @Test
    void findByUserAndFileType() {
        final User user = userRepository.findByName("user")
                .orElseThrow(NotLoggedInException::new);
        final List<HostingFile> cssFiles = hostingFileRepository.findByUserAndFileType(user, FileType.CSS).orElseThrow(() -> new IllegalStateException());
        assertThat(cssFiles).hasSize(2);
    }

    @DisplayName("user가 업로드한 파일을 User와 FilePath로 찾아올 수 있다.")
    @Test
    void findByUserAndFilePath() {
        String cssFilePath = "CSSFilePath1";

        final User user = userRepository.findByName("user")
                .orElseThrow(NotLoggedInException::new);
        final HostingFile file = hostingFileRepository.findByUserAndFilePath(user, cssFilePath).orElseThrow(() -> new IllegalStateException());;
        assertThat(file.getFilePath()).isEqualTo(cssFilePath);
        assertThat(file.getFileType()).isEqualTo(FileType.CSS);
    }
}