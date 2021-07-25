package webhosting.webhosting.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new User("name", "imageUrl", "socialId1"));
    }

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
}