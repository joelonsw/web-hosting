package webhosting.webhosting.login.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GithubAccessToken {
    private String token_type;
    private String scope;
    private String access_token;
}
