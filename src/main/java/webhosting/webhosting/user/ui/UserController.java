package webhosting.webhosting.user.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import webhosting.webhosting.user.service.UserService;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage.html";
    }
}
