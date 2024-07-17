package likelion.MZConnent.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion.MZConnent.dto.ApiResponseJson;
import likelion.MZConnent.jwt.token.TokenInfo;
import likelion.MZConnent.jwt.token.TokenStatus;
import likelion.MZConnent.jwt.token.TokenValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Map;

/**
 * 인증(Authentication) 예외가 발생했을때 처리하는 클래스
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String VALIDATION_RESULT_KEY = "result";
    private static final String ERROR_MESSAGE_KEY = "errMsg";

    private static final String TOKEN_INFO_KEY = "tokenInfo";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // JwtFilter에서 인증 결과를 전달할 때 TokenValidationResult를 담아서 넘겨줌

        TokenValidationResult result = (TokenValidationResult) request.getAttribute(VALIDATION_RESULT_KEY);
        String errorMessage = result.getTokenStatus().getMessage();
        TokenInfo tokenInfo = null;

        if (result.getTokenStatus() == TokenStatus.TOKEN_REFRESHED) {
            tokenInfo = result.getTokenInfo();
        }

        sendError(response, errorMessage, tokenInfo);
    }

    private void sendError(HttpServletResponse response, String message, TokenInfo tokenInfo) throws IOException {
        ApiResponseJson responseJson;

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (tokenInfo != null) {
            responseJson = new ApiResponseJson(HttpStatus.OK, message, tokenInfo);
        } else {
            responseJson = new ApiResponseJson(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED), message, null);
        }

        String jsonToString = objectMapper.writeValueAsString(responseJson);

        response.getWriter().write(jsonToString);
    }

}
