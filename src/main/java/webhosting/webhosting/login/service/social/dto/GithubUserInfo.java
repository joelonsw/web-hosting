package webhosting.webhosting.login.service.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webhosting.webhosting.user.domain.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GithubUserInfo {
    private Long id;
    private String name;
    private String avatar_url;

    public User toUser() {
        return new User(name, avatar_url, String.valueOf(id));
    }
}
