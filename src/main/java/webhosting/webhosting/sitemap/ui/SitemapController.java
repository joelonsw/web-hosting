package webhosting.webhosting.sitemap.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import webhosting.webhosting.sitemap.service.SitemapService;

@RestController
@RequiredArgsConstructor
public class SitemapController {

    private final SitemapService sitemapService;

    @GetMapping(value = "/sitemap.xml", produces = "application/xml")
    public ResponseEntity<String> getSitemap() {
        String sitemap = sitemapService.getSitemap();
        return ResponseEntity.ok(sitemap);
    }
}
