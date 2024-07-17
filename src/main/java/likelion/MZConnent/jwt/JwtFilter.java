package likelion.MZConnent.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.MZConnent.jwt.token.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * JWT를 통한 인증을 위한 filter 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // jwt token이 유효한지 1차적으로 확인하기 위한 정규표현식
    private static final String BEARER_REGEX = "Bearer ([a-zA-Z0-9_\\-\\+\\/=]+)\\.([a-zA-Z0-9_\\-\\+\\/=]+)\\.([a-zA-Z0-9_.\\-\\+\\/=]*)";
    private static final Pattern BEARER_PATTERN = Pattern.compile(BEARER_REGEX);

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String token = extractToken(request);


        if (!StringUtils.hasText(token)) { // 토큰 값이 존재하지 않는 경우
            handleMissingToken(request, response, filterChain);
            return;
        }

        TokenValidationResult tokenValidationResult = tokenProvider.validateToken(token);


        if (!tokenValidationResult.isValid()) { // 토큰이 유효하지 않는 경우
            handleWrongToken(request, response, filterChain, tokenValidationResult);
            return;
        }


        if (tokenValidationResult.getTokenType().equals(TokenType.ACCESS)) {         // access token인 경우
            // TODO: blacklist에 있는지 확인
            // 정상 토큰 처리
            handleValidAccessToken(token, tokenValidationResult);
            filterChain.doFilter(request, response);
        }
        else if (tokenValidationResult.getTokenType().equals(TokenType.REFRESH)){ // TODO: refresh token인 경우 -> refreshtokenlist에 있는지 확인 -> accesstoken 재발급

        } else {
            // TODO: 둘 다 아닌 경우
        }
    }


    private void handleValidAccessToken(String token, TokenValidationResult tokenValidationResult) {
        // securityContext에 authentication을 넣어주어 사용자 인증 처리
        Authentication authentication = tokenProvider.getAuthentication(token, tokenValidationResult.getClaims());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("AUTH 성공: {}", authentication.getName());
    }

    private void handleWrongToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, TokenValidationResult tokenValidationResult) throws IOException, ServletException {
        request.setAttribute("result", tokenValidationResult);
        filterChain.doFilter(request, response);
    }

    private void handleMissingToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setAttribute("result", new TokenValidationResult(TokenStatus.WRONG_AUTH_HEADER, null, null, null));
        filterChain.doFilter(request, response);
    }

    // 토큰을 추출하는 함수
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER); // Authorization header에서 value를 꺼냄

        if(bearerToken != null && BEARER_PATTERN.matcher(bearerToken).matches()) { // header에 토큰 값이 존재하고, 정규표현식으로 검사했을 때 토큰에 문제가 없는 경우
            return bearerToken.substring(7); //토큰 값에 bearer만 떼준 다음 return
        }

        return null;
    }
}
