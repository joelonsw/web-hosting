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
public class FacebookUserInfo {
    public static final String DEFAULT_IMAGE = "https://user-images.githubusercontent.com/61370901/126897909-ff4e1c94-2303-4172-a878-70b7e8d96893.png";
    private String id;
    private String name;

    public User toEntity() {
        return new User(name, DEFAULT_IMAGE, id);
    }
}
