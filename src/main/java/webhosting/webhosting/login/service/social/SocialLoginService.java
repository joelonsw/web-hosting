package webhosting.webhosting.login.service.social;

import webhosting.webhosting.user.domain.User;

public interface SocialLoginService {
    String generateRedirectUrl();

    User generateUser(String code);
}
