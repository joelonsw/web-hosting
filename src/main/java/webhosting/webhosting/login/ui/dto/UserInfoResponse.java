package webhosting.webhosting.login.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webhosting.webhosting.login.domain.User;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    private String name;
    private String imageUrl;

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(user.getName(), user.getImageUrl());
    }
}
