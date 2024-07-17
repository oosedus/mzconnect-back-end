package likelion.MZConnent.jwt.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.member.Role;
import likelion.MZConnent.jwt.blacklist.AccessTokenBlackList;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.jwt.token.refreshToken.RefreshTokenList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * 토큰을 생성하고 검증하는 클래스
 */
@Slf4j
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String TOKEN_ID_KEY = "tokenId";
    private static final String TOKEN_TYPE = "tokeyType";
    private static final String USERNAME_KEY = "username";

    private final Key hashKey;
    private final long accessTokenValidityMilliseconds;
    private final long refreshTokenValidityMilliseconds;

    private final AccessTokenBlackList accessTokenBlackList;
    private final RefreshTokenList refreshTokenList;

    public TokenProvider(String secrete, long accessTokenValidationInSeconds, long refreshTokenValidityMilliseconds, AccessTokenBlackList accessTokenBlackList, RefreshTokenList refreshTokenList) {
        byte[] keyBytes = Decoders.BASE64.decode(secrete);
        this.hashKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityMilliseconds = accessTokenValidationInSeconds * 1000;
        this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds * 1000;
        this.accessTokenBlackList = accessTokenBlackList;
        this.refreshTokenList = refreshTokenList;
    }


    // 토큰 발행 후 토큰 정보를 return하는 함수
    public TokenResponse createToken(Member member) {
        long currentTime = (new Date()).getTime();

        // access 토큰 발행
        TokenInfo accessTokenInfo = getToken(member.getEmail(), currentTime, TokenType.ACCESS, this.accessTokenValidityMilliseconds);

        // refresh 토큰 발행
        TokenInfo refreshTokenInfo = getToken(member.getEmail(), currentTime, TokenType.REFRESH, this.refreshTokenValidityMilliseconds);

        return TokenResponse.builder()
                .email(member.getEmail())
                .accessToken(accessTokenInfo)
                .refreshToken(refreshTokenInfo)
                .build();
    }


    // access token 재발행 후 토큰 정보를 return하는 함수
    public TokenResponse recreateAccessToken(Claims claims) {
        long currentTime = (new Date()).getTime();

        // access 토큰 발행
        TokenInfo accessTokenInfo = getToken(claims.getSubject(), currentTime, TokenType.ACCESS, this.accessTokenValidityMilliseconds);


        return TokenResponse.builder()
                .email(claims.getSubject())
                .accessToken(accessTokenInfo)
                .build();
    }


    private TokenInfo getToken(String email, long currentTime, TokenType tokenType, long tokenValidityMilliseconds) {
        Date tokenExpireTime = new Date(currentTime + tokenValidityMilliseconds);
        String accessTokenId = UUID.randomUUID().toString();

        String token =  issueToken(email, tokenType, Role.USER, accessTokenId, tokenExpireTime);

        return TokenInfo.builder()
                .type(tokenType)
                .expireTime(tokenExpireTime)
                .id(accessTokenId)
                .token(token)
                .build();
    }

    // 토큰을 발행하는 함수
    private String issueToken(String email, TokenType tokenType, Role role, String tokenId, Date tokenExpireTime) {
        return Jwts.builder()
                .setSubject(email)
                .claim(TOKEN_TYPE, tokenType)
                .claim(AUTHORITIES_KEY, role)
                .claim(TOKEN_ID_KEY, tokenId)
                .signWith(hashKey, SignatureAlgorithm.HS512)
                .setExpiration(tokenExpireTime)
                .compact();
    }


    // 토큰 검증 후 검증 결과 return하는 함수
    public TokenValidationResult validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(hashKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 정상 토큰일 시
            return new TokenValidationResult(TokenStatus.TOKEN_VALID, TokenType.valueOf(claims.get(TOKEN_TYPE, String.class)), claims.get(TOKEN_ID_KEY, String.class), claims);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰");
            Claims claims = e.getClaims();
            return new TokenValidationResult(TokenStatus.TOKEN_EXPIRED, TokenType.valueOf(claims.get(TOKEN_TYPE, String.class)),
                    claims.get(TOKEN_ID_KEY, String.class), null);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 서명");
            return new TokenValidationResult(TokenStatus.TOKEN_HASH_NOT_SUPPORTED, null, null, null);
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰");
            return new TokenValidationResult(TokenStatus.TOKEN_WRONG_SIGNATURE, null, null, null);
        }
    }

    // access 토큰과 claims을 전달받아 UsernamePasswordAuthenticationToken을 생성해 전달하는 함수
    public Authentication getAuthentication(String token, Claims claims) {
        // claims에서 권한 정보를 받아와 파싱
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY)  .toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserPrinciple principle = new UserPrinciple(claims.getSubject(), claims.get(USERNAME_KEY, String.class), authorities);

        return new UsernamePasswordAuthenticationToken(principle, token, authorities);
    }
}
