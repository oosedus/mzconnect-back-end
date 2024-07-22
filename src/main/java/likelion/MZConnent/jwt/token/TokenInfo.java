package likelion.MZConnent.jwt.token;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString(exclude = {"token"})
public class TokenInfo {
    private TokenType type;
    private String token;
    private Date expireTime;
    private String id;

    @Builder
    public TokenInfo(TokenType type, String token, Date expireTime, String id) {
        this.type = type;
        this.token = token;
        this.expireTime = expireTime;
        this.id = id;
    }
}
