package webhosting.webhosting.ui;

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
                              @RequestParam("files") List<MultipartFile> files) throws IOException {
        hostingService.saveFile(userId, files);
        return "redirect:/";
    }
}
