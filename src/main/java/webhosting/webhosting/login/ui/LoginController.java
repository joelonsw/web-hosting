package webhosting.webhosting.login.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import webhosting.webhosting.login.domain.LoginPrincipal;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.login.service.LoginService;
import webhosting.webhosting.login.ui.dto.UserInfoResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login/userinfo")
    public ResponseEntity<UserInfoResponse> getUserInfo(@LoginPrincipal User user) {
        UserInfoResponse userInfoResponse = loginService.getUserInfo(user);
        return ResponseEntity.ok(userInfoResponse);
    }

    @GetMapping("/login/{social}")
    public String generateRedirectUrl(@PathVariable String social) {
        String redirectUrl = loginService.generateRedirectUrl(social);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/login/oauth/{social}")
    public String loginWithSocial(@PathVariable String social, @RequestParam String code, HttpServletRequest request) {
        String socialId = loginService.loginWithSocial(social, code);
        HttpSession session = request.getSession();
        session.setAttribute("socialId", socialId);
        return "redirect:/";
    }
}
