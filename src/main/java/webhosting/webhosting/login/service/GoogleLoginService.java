package webhosting.webhosting.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webhosting.webhosting.member.domain.User;
import webhosting.webhosting.login.exception.GoogleUserGenerationException;
import webhosting.webhosting.login.util.GoogleAccessToken;
import webhosting.webhosting.login.util.GoogleUserInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleLoginService {

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

    @Value("${oauth.google.oauth_url}")
    private String oauthUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public GoogleLoginService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        restTemplate = new RestTemplate();
    }

    public String generateRedirectUrl() {
        return oauthUrl +
                "?client_id=" + clientId +
                "&scope=profile" +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri;
    }

    public String generateAccessToken(String code) {
        final RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUrl, params, String.class);
        return responseEntity.getBody();
    }

    public User generateUser(String accessToken) {
        try {
            final String userInfo = getUserInfo(accessToken);
            final GoogleUserInfo googleUserInfo = objectMapper.readValue(userInfo, GoogleUserInfo.class);
            return new User(googleUserInfo.getName(), googleUserInfo.getPicture(), googleUserInfo.getSub());
        } catch (JsonProcessingException e) {
            throw new GoogleUserGenerationException(e.getMessage());
        }
    }

    private String getUserInfo(String accessToken) throws JsonProcessingException {
        final GoogleAccessToken googleAccessToken = objectMapper.readValue(accessToken, GoogleAccessToken.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", googleAccessToken.getToken_type() + " " + googleAccessToken.getAccess_token());

        final ResponseEntity<String> userInfoEntity =
                restTemplate.exchange(userInfoUrl, HttpMethod.GET, new HttpEntity<>("parameters", headers), String.class);
        return userInfoEntity.getBody();
    }
}
