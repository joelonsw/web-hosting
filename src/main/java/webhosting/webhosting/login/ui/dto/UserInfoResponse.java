package webhosting.webhosting.login.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import webhosting.webhosting.user.domain.User;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    private String name;
    private String imageUrl;
    private boolean deployed;

    public static UserInfoResponse from(User user, boolean deployed) {
        return new UserInfoResponse(user.getName(), user.getImageUrl(), deployed);
    }
}
