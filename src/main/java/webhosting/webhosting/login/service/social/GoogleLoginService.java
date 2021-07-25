package webhosting.webhosting.login.service.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webhosting.webhosting.login.service.social.dto.AccessToken;
import webhosting.webhosting.login.service.social.dto.GoogleUserInfo;
import webhosting.webhosting.user.domain.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleLoginService implements SocialLoginService {

    @Value("${oauth.google.client_id}")
    private String clientId;

    @Value("${oauth.google.client_secret}")
    private String clientSecret;

    @Value("${oauth.google.token_url}")
    private String tokenUrl;

    @Value("${oauth.google.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.google.user_info_url}")
    private String userInfoUrl;

    @Value("${oauth.google.oauth_redirect_url}")
    private String oauthRedirectUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String generateRedirectUrl() {
        return oauthRedirectUrl +
                "?client_id=" + clientId +
                "&scope=profile" +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri;
    }

    @Override
    public User generateUser(String code) {
        final AccessToken accessToken = generateAccessToken(code);
        return generateUser(accessToken);
    }

    private AccessToken generateAccessToken(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");

        final ResponseEntity<AccessToken> responseEntity = restTemplate.postForEntity(tokenUrl, params, AccessToken.class);
        return responseEntity.getBody();
    }

    private User generateUser(AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", accessToken.getToken_type() + " " + accessToken.getAccess_token());

        final ResponseEntity<GoogleUserInfo> userInfoEntity =
                restTemplate.exchange(userInfoUrl, HttpMethod.GET, new HttpEntity<>("parameters", headers), GoogleUserInfo.class);
        return userInfoEntity.getBody().toUser();
    }
}
