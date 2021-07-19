package webhosting.webhosting.hosting.ui;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.hosting.service.HostingService;
import webhosting.webhosting.login.domain.LoginPrincipal;
import webhosting.webhosting.login.domain.User;

import java.util.List;

@Controller
public class HostingController {
    private HostingService hostingService;

    public HostingController(HostingService hostingService) {
        this.hostingService = hostingService;
    }

    @GetMapping("/deploy")
    public String deployPage() {
        return "deploy.html";
    }

    @PostMapping("/pages")
    public String makeWebPage(@LoginPrincipal User user,
                              @RequestParam("htmlFile") MultipartFile htmlFile,
                              @RequestParam(value = "cssFile", required = false) List<MultipartFile> cssFiles,
                              @RequestParam(value = "jsFile", required = false) List<MultipartFile> jsFiles) {
        String deployedUrl = hostingService.saveFile(user, htmlFile, cssFiles, jsFiles);
        return "redirect:" + deployedUrl;
    }

    @GetMapping(value = "/pages/{userName}", produces = "text/html")
    public ResponseEntity<String> showUserWebPage(@PathVariable String userName) {
        final String result = hostingService.getUserHtmlFile(userName);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping(value = "/pages/{userName}/{resource}", produces = {"text/css", "application/javascript"})
    public ResponseEntity<String> getUserResources(@PathVariable String userName, @PathVariable String resource) {
        final String result = hostingService.getUserResource(userName, resource);
        if (resource.contains(".js")) {
            return ResponseEntity.status(200).contentType(new MediaType("application", "javascript")).body(result);
        }
        return ResponseEntity.status(200).contentType(new MediaType("text", "css")).body(result);
    }
}
