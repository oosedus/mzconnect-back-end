package likelion.MZConnent.api.member;

import jakarta.validation.Valid;
import likelion.MZConnent.dto.ApiResponseJson;
import likelion.MZConnent.dto.member.CreateMemberRequest;
import likelion.MZConnent.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/user/register")
    public ResponseEntity<ApiResponseJson> register(@Valid @RequestBody CreateMemberRequest request, BindingResult bindingResult) { // @Valid를 통해 검증한 결과는 BindingResult를 통해 받아볼 수 있음

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new IllegalArgumentException(errorMessage);
        }

        Long memberId = loginService.createUser(request);
        log.info("계정 생성 성공: {}", memberId);

        return ResponseEntity.ok(new ApiResponseJson(
                HttpStatus.OK, null, Map.of("memberId", memberId)));
    }
}
