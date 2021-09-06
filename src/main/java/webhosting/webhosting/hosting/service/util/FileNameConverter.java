package webhosting.webhosting.hosting.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class FileNameConverter {

    private static String serverPath;
    private static String urlPath;

    private FileNameConverter() {
    }

    public static String generatePageDirectory(String pageName) {
        return serverPath + pageName;
    }

    public static String generateServerFilePath(String pageName, String originalFilename) {
        return serverPath + pageName + "/" + originalFilename;
    }

    public static String generateCssUrlPath(String path) {
        final String tempPath = path.replace(serverPath, "");
        final String[] splitPath = tempPath.split("/", 2);
        return urlPath + "/pages/" + splitPath[0] + "/css/" + splitPath[1];
    }

    public static String generateJsUrlPath(String path) {
        final String tempPath = path.replace(serverPath, "");
        final String[] splitPath = tempPath.split("/", 2);
        return urlPath + "/pages/" + splitPath[0] + "/js/" + splitPath[1];
    }

    @Component
    private static class PathInjector {
        @Value("${hosting-file.server.path}")
        private String serverPath;

        @Value("${hosting-file.url.path}")
        private String urlPath;

        @PostConstruct
        public void injectPath() {
            FileNameConverter.serverPath = serverPath;
            FileNameConverter.urlPath = urlPath;
        }
    }
}
