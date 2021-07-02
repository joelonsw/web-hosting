package webhosting.webhosting.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.service.HostingService;

import java.io.IOException;
import java.util.List;

@Controller
public class HostingController {
    private HostingService hostingService;

    public HostingController(HostingService hostingService) {
        this.hostingService = hostingService;
    }

    @PostMapping("/page")
    public String makeWebPage(@RequestParam("userId") String userId,
                              @RequestParam("htmlFile") MultipartFile htmlFile,
                              @RequestParam("cssFile") List<MultipartFile> cssFiles,
                              @RequestParam("jsFile") List<MultipartFile> jsFiles) throws IOException {
        hostingService.saveFile(userId, htmlFile, cssFiles, jsFiles);
        return "redirect:/";
    }

    @GetMapping(value = "/page/{userId}", produces = "text/html")
    public ResponseEntity<String> showUserWebPage(@PathVariable String userId) throws IOException {
        final String result = hostingService.getUserHtmlFile(userId);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping(value = "/page/{userId}/{resource}", produces = {"text/css", "application/javascript"})
    public ResponseEntity<String> getUserResources(@PathVariable String userId, @PathVariable String resource) throws IOException {
        if (resource.contains(".js")) {
            final String result = hostingService.getUserResource(userId, resource);
            return ResponseEntity.status(200).contentType(new MediaType("application","javascript")).body(result);
        }
        final String result = hostingService.getUserResource(userId, resource);
        return ResponseEntity.status(200).contentType(new MediaType("text","css")).body(result);
    }
}
