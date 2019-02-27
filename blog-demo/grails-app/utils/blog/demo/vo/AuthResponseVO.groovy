package blog.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

class AuthResponseVO {
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("expires_in")
    Integer expiresIn;
    @JsonProperty("refresh_expires_in")
    Integer refreshExpiresIn;
    @JsonProperty("refresh_token")
    String refreshToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("not-before-policy")
    Integer notBeforePolicy;
    @JsonProperty("session_state")
    String sessionState;
    @JsonProperty("scope")
    String scope;
}
