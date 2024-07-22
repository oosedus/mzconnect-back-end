package likelion.MZConnent.jwt.token;

import lombok.Builder;
import lombok.Data;

/**
 * 로그인 시 응답 클래스
 */
@Data
public class TokenResponse {
    private TokenInfo accessToken;
    private TokenInfo refreshToken;
    private String email; // 토큰을 소유한 사용자의 이메일

    @Builder
    public TokenResponse(TokenInfo accessToken, TokenInfo refreshToken, String email) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
    }
}
