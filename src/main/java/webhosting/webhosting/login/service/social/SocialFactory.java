package webhosting.webhosting.login.service.social;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

public enum SocialFactory {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github");

    private final String socialType;
    private SocialLoginService socialLoginService;

    SocialFactory(String socialType) {
        this.socialType = socialType;
    }

    public static SocialFactory of(String socialInput) {
        return Arrays.stream(values())
                .filter(social -> social.socialType.equals(socialInput))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("지원하지 않는 소셜 로그인"));
    }

    public void setSocialLoginService(SocialLoginService socialLoginService) {
        this.socialLoginService = socialLoginService;
    }

    public SocialLoginService getSocialLoginService() {
        return socialLoginService;
    }

    @Component
    @AllArgsConstructor
    private static class SocialLoginServiceInjector {
        private GoogleLoginService googleLoginService;
        private FacebookLoginService facebookLoginService;
        private GithubLoginService githubLoginService;

        @PostConstruct
        private void inject() {
            GOOGLE.setSocialLoginService(googleLoginService);
            FACEBOOK.setSocialLoginService(facebookLoginService);
            GITHUB.setSocialLoginService(githubLoginService);
        }
    }
}
