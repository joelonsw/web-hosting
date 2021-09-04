package webhosting.webhosting.hosting.service.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FileNameConverterTest {

    @DisplayName("서버에 저장할 페이지 디렉토리를 만든다.")
    @Test
    void generatePageDirectory() {
        final String pageName = "hello-world";
        final String pageDirectory = FileNameConverter.generatePageDirectory(pageName);
        assertThat("C:\\\\Users\\\\joel6\\\\Desktop\\\\hello-world").isEqualTo(pageDirectory);
    }

    @DisplayName("서버에 저장할 파일 경로를 만든다.")
    @Test
    void generateServerFilePath() {
        final String pageName = "hello-world";
        final String fileName = "index.html";
        final String filePath = FileNameConverter.generateServerFilePath(pageName, fileName);
        assertThat("C:\\\\Users\\\\joel6\\\\Desktop\\\\hello-world/index.html").isEqualTo(filePath);
    }

    @DisplayName("사용자의 ServerPath를 Css Url로 변경할 수 있다. ")
    @Test
    void generateCssUrlPath() {
        final String pageName = "hello-world";
        final String fileName = "style.css";
        final String serverPath = FileNameConverter.generateServerFilePath(pageName, fileName);

        final String cssUrl = FileNameConverter.generateCssUrlPath(serverPath);
        assertThat("http://localhost:8080/hello-world/css/style.css").isEqualTo(cssUrl);
    }

    @DisplayName("사용자의 ServerPath를 Js Url로 변경할 수 있다.")
    @Test
    void generateJsUrlPath() {
        final String pageName = "hello-world";
        final String fileName = "app.js";
        final String serverPath = FileNameConverter.generateServerFilePath(pageName, fileName);

        final String jsUrl = FileNameConverter.generateJsUrlPath(serverPath);
        assertThat("http://localhost:8080/hello-world/js/app.js").isEqualTo(jsUrl);
    }
}
