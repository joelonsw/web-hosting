package webhosting.webhosting.login.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webhosting.webhosting.member.domain.User;
import webhosting.webhosting.member.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(new User("name", "imageUrl", "socialId"));
    }

    @Test
    void validateDuplicateUserName() {
        User user = new User("name", "imageUrl", "socialId");
        User savedUser = loginService.saveUser(user);
        assertThat(savedUser.getName()).isEqualTo("name(2)");

        user = new User("name", "imageUrl", "socialId");
        savedUser = loginService.saveUser(user);
        assertThat(savedUser.getName()).isEqualTo("name(3)");
    }
}