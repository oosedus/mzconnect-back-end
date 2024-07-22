package likelion.MZConnent.jwt.token;

import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * 토큰의 검증 결과를 담고 있는 클래스
 */
@Getter
@ToString
@Data
public class TokenValidationResult {
    private TokenStatus tokenStatus;
    private TokenType tokenType;
    private String tokenId;
    private Claims claims;
    private TokenResponse tokenResponse = null;

    public TokenValidationResult(TokenStatus tokenStatus, TokenType tokenType, String tokenId, Claims claims) {
        this.tokenStatus = tokenStatus;
        this.tokenType = tokenType;
        this.tokenId = tokenId;
        this.claims = claims;
    }

    public TokenValidationResult(TokenStatus tokenStatus, TokenType tokenType, String tokenId, Claims claims, TokenResponse tokenResponse) {
        this.tokenStatus = tokenStatus;
        this.tokenType = tokenType;
        this.tokenId = tokenId;
        this.claims = claims;
        this.tokenResponse = tokenResponse;
    }

    public String getEmail() {
        if (claims == null) {
            throw new IllegalStateException("claim이 존재하지 않습니다.");
        }
        return claims.getSubject();
    }


    // 토큰이 정상 토큰인지 확인하는 함수
    public boolean isValid() {
        return TokenStatus.TOKEN_VALID == this.tokenStatus;
    }
}
