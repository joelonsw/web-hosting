package webhosting.webhosting.login.service.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webhosting.webhosting.login.service.social.dto.AccessToken;
import webhosting.webhosting.login.service.social.dto.FacebookUserInfo;
import webhosting.webhosting.user.domain.User;

@Service
public class FacebookLoginService implements SocialLoginService {
    @Value("${oauth.facebook.client_id}")
    private String clientId;

    @Value("${oauth.facebook.client_secret}")
    private String clientSecret;

    @Value("${oauth.facebook.token_url}")
    private String tokenUrl;

    @Value("${oauth.facebook.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.facebook.user_info_url}")
    private String userInfoUrl;

    @Value("${oauth.facebook.oauth_redirect_url}")
    private String oauthRedirectUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public String generateRedirectUrl() {
        return oauthRedirectUrl +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri;
    }

    @Override
    public User generateUser(String code) {
        final AccessToken accessToken = generateAccessToken(code);
        return generateUser(accessToken);
    }

    private AccessToken generateAccessToken(String code) {
        String accessTokenUrl = tokenUrl +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&client_secret=" + clientSecret +
                "&code=" + code;
        System.out.println("accessTokenUrl = " + accessTokenUrl);
        final ResponseEntity<AccessToken> response = restTemplate.getForEntity(accessTokenUrl, AccessToken.class);
        return response.getBody();
    }

    private User generateUser(AccessToken accessToken) {
        String userInfoFullUrl = userInfoUrl +
                "?fields=id,name" +
                "&access_token=" + accessToken.getAccess_token();

        final ResponseEntity<FacebookUserInfo> userInfoEntity = restTemplate.getForEntity(userInfoFullUrl, FacebookUserInfo.class);
        return userInfoEntity.getBody().toEntity();
    }
}
