package webhosting.webhosting.login.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import webhosting.webhosting.login.exception.NotLoggedInException;
import webhosting.webhosting.member.domain.User;
import webhosting.webhosting.member.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByName() {
        //given
        final User user1 = new User("user1", "image1", "socialId");
        userRepository.save(user1);

        //when then
        final User saved = userRepository.findBySocialId("socialId")
                .orElseThrow(NotLoggedInException::new);
        assertThat(saved).isEqualTo(user1);
    }
}