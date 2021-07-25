package webhosting.webhosting.login.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webhosting.webhosting.login.domain.LoginPrincipal;
import webhosting.webhosting.member.domain.User;
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
        return ResponseEntity.ok(UserInfoResponse.from(user));
    }

    @GetMapping("/login/google")
    public String loginWithGoogle() {
        String redirectUrl = loginService.generateGoogleRedirectUrl();
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/login/oauth/google")
    public String oauthGoogle(@RequestParam String code, HttpServletRequest request) {
        String name = loginService.oauthGoogle(code);
        HttpSession session = request.getSession();
        session.setAttribute("name", name);
        return "redirect:/";
    }
}
