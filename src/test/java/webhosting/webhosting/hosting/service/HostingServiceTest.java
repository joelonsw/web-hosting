package webhosting.webhosting.hosting.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HostingServiceTest {

    @Autowired
    private HostingService hostingService;

    @DisplayName("Yeongsang Jo는 Yeongsang Jo로 인코딩된다.")
    @Test
    void encodeEnglish() {
        String userName = "Yeongsang Jo";
        final String encoded = hostingService.encodeInUTF8(userName);
        assertThat(encoded).isEqualTo("Yeongsang Jo");
    }

    @DisplayName("조영상은 조영상으로 인코딩된다.")
    @Test
    void encodeKorean() {
        String userName = "조영상";
        final String encoded = hostingService.encodeInUTF8(userName);
        assertThat(encoded).isEqualTo("조영상");
    }
}