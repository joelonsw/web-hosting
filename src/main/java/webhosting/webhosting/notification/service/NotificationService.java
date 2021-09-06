package webhosting.webhosting.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Value("${slack.url}")
    private String slackUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendSlackDeployMessage(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("text", message + " 신규 배포됨");
        restTemplate.postForObject(slackUrl, body, String.class);
    }

    public void sendSlackExceptionMessage(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("text", message + " 예외 발생!");
        restTemplate.postForObject(slackUrl, body, String.class);
    }
}
