package likelion.MZConnent.jwt.token;

import lombok.Getter;

@Getter
public enum TokenStatus {
    TOKEN_VALID("정상 토큰입니다."),
    TOKEN_EXPIRED("토큰이 만료되었습니다."),
    TOKEN_IS_BLACKLIST("폐기된 토큰입니다."),
    TOKEN_WRONG_SIGNATURE("잘못된 토큰입니다."),
    TOKEN_HASH_NOT_SUPPORTED("지원하지 않는 형식의 토큰입니다."),
    WRONG_AUTH_HEADER("[Bearer ]로 시작하는 토큰이 없습니다."),
    TOKEN_WRONG_TYPE("지원하지 않는 타입의 토큰입니다."),
    TOKEN_REFRESHED("토큰이 재발급되었습니다");

    private final String message;

    TokenStatus(String message) {
        this.message = message;
    }

}
