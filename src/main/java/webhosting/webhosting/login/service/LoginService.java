package webhosting.webhosting.login.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import webhosting.webhosting.login.util.AccessToken;
import webhosting.webhosting.login.util.GithubAccessToken;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.service.UserService;

@Service
@AllArgsConstructor
public class LoginService {
    private GoogleLoginService googleLoginService;
    private FacebookLoginService facebookLoginService;
    private GithubLoginService githubLoginService;
    private UserService userService;

    public String generateGoogleRedirectUrl() {
        return googleLoginService.generateRedirectUrl();
    }

    public String oauthGoogle(String code) {
        final AccessToken accessToken = googleLoginService.generateAccessToken(code);
        final User user = googleLoginService.generateUser(accessToken);
        return userService.register(user);
    }

    public String generateFacebookRedirectUrl() {
        return facebookLoginService.generateRedirectUrl();
    }

    public String oauthFacebook(String code) {
        final AccessToken accessToken = facebookLoginService.generateAccessToken(code);
        final User user = facebookLoginService.generateUser(accessToken);
        return userService.register(user);
    }

    public String generateGithubRedirectUrl() {
        return githubLoginService.generateRedirectUrl();
    }

    public String oauthGithub(String code) {
        final GithubAccessToken accessToken = githubLoginService.generateAccessToken(code);
        final User user = githubLoginService.generateUser(accessToken);
        return userService.register(user);
    }
}
