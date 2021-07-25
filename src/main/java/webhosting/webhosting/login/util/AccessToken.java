package webhosting.webhosting.login.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccessToken {
    private String access_token;
    private Integer expires_in;
    private String token_type;
}
