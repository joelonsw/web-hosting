package webhosting.webhosting.hosting.ui;

import org.springframework.http.MediaType;

public class MediaTypeFactory {
    private MediaTypeFactory() {
    }

    public static MediaType css() {
        return new MediaType("text", "css");
    }

    public static MediaType js() {
        return new MediaType("application", "javascript");
    }
}
