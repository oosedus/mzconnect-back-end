package likelion.MZConnent.jwt.token;

import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.member.Role;
import likelion.MZConnent.jwt.blacklist.AccessTokenBlackList;
import likelion.MZConnent.jwt.token.refreshToken.RefreshTokenList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TokenProviderTest {
    private final String secrete = "CGhpIGZhc2RmZCBuYW1lIGlzIGNoYXllb2l1bWcgZWFkZmEgYXNkZm5kIGltCGhpIG15IG5hYWRzZiBhZHNmZGZhc2QgZnNhZGZhc2QgbWUgaXMgY2hheWVvaXVtZyBlbmQgZHNhZmFkc2ZpbQoIaGlhIGFzZGFzZGZhc2RteSBuYWV3ZmFzZGZhZHNmYXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYWZzZXdhcnFld3FyZXdxcmV3cWZhc2RkZmFzZGRhYWRzZm1lIGlzIGNoYXllb2l1bWcgZWFkZmEgYXNkZm5kIGltCGhpIG15IG5hYWRzZiBhZHNmZGZhc2QgZnNhZGZhc2QgbWUgaXMgY2hheWVvaXVtZyBlbmQgaW0KCGhkZmkgYW15IG5hbWUgaXMgY3NkYWZhc2RmaGF5ZW9pdW1nIGVhZGZhIGFzZGZuZCBpbQhoaSBteSBuYWFkc2YgYWRzZmRmYXNkIHNhZGZmc2FkZmFzZCBtZSBpcyBjaGF5ZW9pdW1nIGVuZCBpbQoIaGkgYXNkZmFzIGZteSBuYW1lIGlzIGNoYXllb2l1bWcgZWFkZmEgYXNkZm5kIGltCGhpIG15IG5hYWRzZiBhZHNmZGZhc2QgZnNhZGZhc2QgbWUgaWFzZGZzIGNoYXllb2l1bWcgZW5kIGltCgo=";


    private final Long accessTokenValidTimeInSeconds = 3L; // access 토큰 만료 시간 3초로 세팅

    private final Long refreshTokenValidTimeInSeconds = 360000L; // refresh 토큰 만료 시간 360000초로 세팅

    @Autowired
    AccessTokenBlackList accessTokenBlackList;

    @Autowired
    RefreshTokenList refreshTokenList;

    private final TokenProvider tokenProvider = new TokenProvider(secrete, accessTokenValidTimeInSeconds, refreshTokenValidTimeInSeconds, accessTokenBlackList, refreshTokenList);


    @Test
    void createToken() {
        Member member = getMember();

        TokenResponse token = tokenProvider.createToken(member);
        log.info("access token={}", token.getAccessToken().getToken());
        log.info("refresh token={}", token.getRefreshToken().getToken());
    }

    private Member getMember() {
        return Member.builder()
                .email("test@test.ac.kr")
                .password("test1234@!!")
                .realname("테스트")
                .username("test")
                .role(Role.USER)
                .age(40)
                .build();
    }
}