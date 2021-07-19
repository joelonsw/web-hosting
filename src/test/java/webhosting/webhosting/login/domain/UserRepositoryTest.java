package webhosting.webhosting.login.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByName() {
        //given
        final User user1 = new User("user1", "image1");
        userRepository.save(user1);

        //when then
        final User saved = userRepository.findByName("user1");
        assertThat(saved).isEqualTo(user1);
    }
}