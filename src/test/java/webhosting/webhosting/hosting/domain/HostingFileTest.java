package webhosting.webhosting.hosting.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webhosting.webhosting.hosting.service.util.FileNameConverter;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HostingFileTest extends HostingFileFixture {

    @DisplayName("파일 타입을 검색할 수 있다.")
    @Test
    void fileTypeSearch() {
        assertThat(htmlFile.isHtml()).isTrue();
        assertThat(htmlFile.isCss()).isFalse();
        assertThat(htmlFile.isJs()).isFalse();

        assertThat(cssFile1.isHtml()).isFalse();
        assertThat(cssFile1.isCss()).isTrue();
        assertThat(cssFile1.isJs()).isFalse();

        assertThat(jsFile1.isHtml()).isFalse();
        assertThat(jsFile1.isCss()).isFalse();
        assertThat(jsFile1.isJs()).isTrue();
    }

    @DisplayName("HostingFiles에서 필요한 파일을 추출할 수 있다.")
    @Test
    void hostingFilesTest() {
        final HostingFiles hostingFiles = new HostingFiles(Arrays.asList(htmlFile, cssFile1, cssFile2, jsFile1, jsFile2));

        final HostingFile findHtmlFile = hostingFiles.findHtmlFile();
        assertThat(findHtmlFile).isEqualTo(htmlFile);

        final List<HostingFile> cssFiles = hostingFiles.findCssFiles();
        assertThat(cssFiles).contains(cssFile1, cssFile2);

        final List<HostingFile> jsFiles = hostingFiles.findJsFiles();
        assertThat(jsFiles).contains(jsFile1, jsFile2);
    }
}