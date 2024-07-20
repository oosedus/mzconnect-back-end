package likelion.MZConnent.exception;


import com.fasterxml.jackson.core.JsonParseException;
import likelion.MZConnent.dto.ApiResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * 예외 응답 처리 클래스
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiResponseJson>  handleBadRequestException(Exception e) {
        return new ResponseEntity<>( new ApiResponseJson(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseJson>  handleHttpMessageNotReadableException(Exception e) {

        String message = e.getMessage();
        int startIndex = message.indexOf("problem: ") + "problem: ".length();
        String errorMessage = message.substring(startIndex);

        return new ResponseEntity<>( new ApiResponseJson(HttpStatus.BAD_REQUEST, errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseJson> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>( new ApiResponseJson(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다."),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
