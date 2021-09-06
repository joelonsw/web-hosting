package webhosting.webhosting.hosting.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.hosting.service.HostingService;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class HostingController {

    private final HostingService hostingService;

    public HostingController(HostingService hostingService) {
        this.hostingService = hostingService;
    }

    @GetMapping("/validation")
    public ResponseEntity<Boolean> validatePageName(@RequestParam String pageName) {
        pageName = new String(pageName.getBytes(), StandardCharsets.UTF_8);
        Boolean isUsable = hostingService.checkPageUsable(pageName);
        return ResponseEntity.ok(isUsable);
    }

    @PostMapping("/deploy")
    public ResponseEntity<Void> deploy(@RequestParam String pageName,
                                       @RequestParam("htmlFile") MultipartFile htmlFile,
                                       @RequestParam(value = "cssFile", required = false) List<MultipartFile> cssFiles,
                                       @RequestParam(value = "jsFile", required = false) List<MultipartFile> jsFiles) {
        pageName = new String(pageName.getBytes(), StandardCharsets.UTF_8);
        hostingService.saveFile(pageName, htmlFile, cssFiles, jsFiles);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{pageName}", produces = "text/html")
    public ResponseEntity<String> getPage(@PathVariable String pageName) {
        pageName = new String(pageName.getBytes(), StandardCharsets.UTF_8);
        final String result = hostingService.getPage(pageName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/{pageName}/css/{resource}", produces = {"text/css"})
    public ResponseEntity<String> getCss(@PathVariable String pageName, @PathVariable String resource) {
        pageName = new String(pageName.getBytes(), StandardCharsets.UTF_8);
        final String result = hostingService.getResource(pageName, resource);
        return ResponseEntity.ok().contentType(MediaTypeFactory.css()).body(result);
    }

    @GetMapping(value = "/{pageName}/js/{resource}", produces = {"application/javascript"})
    public ResponseEntity<String> getJs(@PathVariable String pageName, @PathVariable String resource) {
        pageName = new String(pageName.getBytes(), StandardCharsets.UTF_8);
        final String result = hostingService.getResource(pageName, resource);
        return ResponseEntity.ok().contentType(MediaTypeFactory.js()).body(result);
    }
}
