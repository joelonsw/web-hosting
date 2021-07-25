package webhosting.webhosting.login.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webhosting.webhosting.login.util.GithubAccessToken;
import webhosting.webhosting.login.util.GithubUserInfo;
import webhosting.webhosting.user.domain.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GithubLoginService {

    @Value("${oauth.github.client_id}")
    private String clientId;

    @Value("${oauth.github.client_secret}")
    private String clientSecret;

    @Value("${oauth.github.token_url}")
    private String tokenUrl;

    @Value("${oauth.github.redirect_uri}")
    private String redirectUri;

    @Value("${oauth.github.user_info_url}")
    private String userInfoUrl;

    @Value("${oauth.github.oauth_redirect_url}")
    private String oauthRedirectUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateRedirectUrl() {
        return oauthRedirectUrl +
                "?client_id=" + clientId +
                "&scope=read:user";
    }

    public GithubAccessToken generateAccessToken(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);

        final ResponseEntity<GithubAccessToken> responseEntity = restTemplate.postForEntity(tokenUrl, params, GithubAccessToken.class);
        return responseEntity.getBody();
    }

    public User generateUser(GithubAccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", accessToken.getToken_type() + " " + accessToken.getAccess_token());

        final ResponseEntity<GithubUserInfo> userInfoEntity =
                restTemplate.exchange(userInfoUrl, HttpMethod.GET, new HttpEntity<>("parameters", headers), GithubUserInfo.class);
        return userInfoEntity.getBody().toUser();
    }
}
