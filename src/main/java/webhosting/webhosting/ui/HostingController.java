package webhosting.webhosting.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webhosting.webhosting.service.HostingService;

import java.io.IOException;
import java.util.List;

@Controller
public class HostingController {
    private HostingService hostingService;

    @PostMapping("/page")
    public String makeWebPage(@RequestParam("files") List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            System.out.println(file.getName());
            System.out.println(file.getContentType());
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getBytes());
            System.out.println("------------------------");
//            hostingService.saveFile(file);
        }

        return "index";
    }
}
