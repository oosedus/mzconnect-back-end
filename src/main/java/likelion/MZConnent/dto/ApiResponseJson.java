package likelion.MZConnent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * 응답 커스텀 클래스
 */
@Getter
@ToString
@NoArgsConstructor
public class ApiResponseJson {
    public HttpStatus httpStatus;
    public String message = null;
    public Object data;

    public ApiResponseJson(HttpStatus httpStatus, String message, Object data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
    }

    public ApiResponseJson(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = null;
    }
}
