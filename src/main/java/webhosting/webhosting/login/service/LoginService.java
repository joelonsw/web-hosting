package webhosting.webhosting.login.service;

import org.springframework.stereotype.Service;
import webhosting.webhosting.login.domain.User;
import webhosting.webhosting.login.domain.UserRepository;

@Service
public class LoginService {
    private GoogleLoginService googleLoginService;
    private UserRepository userRepository;

    public LoginService(GoogleLoginService googleLoginService, UserRepository userRepository) {
        this.googleLoginService = googleLoginService;
        this.userRepository = userRepository;
    }

    public String generateGoogleRedirectUrl() {
        return googleLoginService.generateRedirectUrl();
    }

    public String oauthGoogle(String code) {
        final String accessToken = googleLoginService.generateAccessToken(code);
        final User user = googleLoginService.generateUser(accessToken);
        userRepository.save(user);
        return user.getName();
    }
}
