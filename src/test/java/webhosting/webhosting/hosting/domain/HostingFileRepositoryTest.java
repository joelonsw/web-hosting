package webhosting.webhosting.hosting.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HostingFileRepositoryTest extends HostingFileFixture {

    @Autowired
    private HostingFileRepository hostingFileRepository;

    @BeforeEach
    void setUp() {
        hostingFileRepository.saveAll(Arrays.asList(htmlFile, cssFile1, cssFile2, jsFile1, jsFile2));
    }

    @DisplayName("이미 등록된 pageName인지 확인한다.")
    @Test
    void existsByPageName() {
        boolean exists = hostingFileRepository.existsByPageName(pageName);
        assertThat(exists).isTrue();

        exists = hostingFileRepository.existsByPageName("nothing");
        assertThat(exists).isFalse();
    }

    @DisplayName("pageName으로 HostingFile을 가져올 수 있다.")
    @Test
    void findByPageName() {
        final Optional<List<HostingFile>> hostingFiles = hostingFileRepository.findByPageName(pageName);
        final List<HostingFile> findHostingFile = hostingFiles.get();
        assertThat(findHostingFile.size()).isEqualTo(5);
    }

    @DisplayName("pageName과 ServerPath로 HostingFile을 가져올 수 있다.")
    @Test
    void findByPageNameAndServerPath() {
        final HostingFile findHtmlFile = hostingFileRepository.findByPageNameAndServerPath(pageName, htmlServerPath).get();
        assertThat(findHtmlFile).isEqualTo(htmlFile);
    }
}
