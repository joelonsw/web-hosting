package webhosting.webhosting.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webhosting.webhosting.hosting.domain.FileType;
import webhosting.webhosting.hosting.domain.HostingFile;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        List<User> users = new ArrayList<>();

        for (int i=0; i<10; i++) {
            final User user = new User("이름" + i, "이미지" + i, "소셜아이디" + i);
            user.addHostingFile(new HostingFile(user, "filePath_HTML_1 "+user.getName(), FileType.HTML));
            user.addHostingFile(new HostingFile(user, "filePath_CSS_2 "+user.getName(), FileType.CSS));
            user.addHostingFile(new HostingFile(user, "filePath_CSS_3 "+user.getName(), FileType.CSS));
            user.addHostingFile(new HostingFile(user, "filePath_CSS_4 "+user.getName(), FileType.CSS));
            user.addHostingFile(new HostingFile(user, "filePath_CSS_5 "+user.getName(), FileType.CSS));
            users.add(user);
            userRepository.save(user);
        }
    }

    @Disabled
    @Test
    void validateDuplicateUserName() {
        User user = new User("name", "imageUrl", "socialId2");
        String socialId = userService.saveAndGetSocialId(user);
        User savedUser = userRepository.findBySocialId(socialId).orElseThrow(IllegalArgumentException::new);
        assertThat(savedUser.getName()).isEqualTo("name(2)");

        user = new User("name", "imageUrl", "socialId3");
        socialId = userService.saveAndGetSocialId(user);
        savedUser = userRepository.findBySocialId(socialId).orElseThrow(IllegalArgumentException::new);
        assertThat(savedUser.getName()).isEqualTo("name(3)");
    }

    @DisplayName("JPA N+1 해결해보자")
    @Test
    void findAllFilePath() {
        final List<String> allFilePath = userService.findAllUserHTMLFileName();
        assertThat(allFilePath).hasSize(10);
    }

    @DisplayName("JPA N+1 실험")
    @Test
    void findResourcePaths() {
        final List<String> resources = userService.findResourcePaths("이름1");
        assertThat(resources).hasSize(5);
    }
}