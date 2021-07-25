package webhosting.webhosting.login.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import webhosting.webhosting.login.service.social.SocialFactory;
import webhosting.webhosting.login.service.social.SocialLoginService;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.service.UserService;

@Service
@AllArgsConstructor
public class LoginService {
    private UserService userService;

    public String generateRedirectUrl(String social) {
        final SocialLoginService socialLoginService = SocialFactory.of(social).getSocialLoginService();
        return socialLoginService.generateRedirectUrl();
    }

    public String loginWithSocial(String social, String code) {
        final SocialLoginService socialLoginService = SocialFactory.of(social).getSocialLoginService();
        final User user = socialLoginService.generateUser(code);
        return userService.saveAndGetSocialId(user);
    }
}
