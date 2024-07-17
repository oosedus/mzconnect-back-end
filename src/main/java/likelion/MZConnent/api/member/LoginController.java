package likelion.MZConnent.api.member;

import jakarta.validation.Valid;
import likelion.MZConnent.dto.ApiResponseJson;
import likelion.MZConnent.dto.member.CreateMemberRequest;
import likelion.MZConnent.dto.member.LoginMemberRequest;
import likelion.MZConnent.dto.member.MemberInfoDto;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.jwt.token.TokenResponse;
import likelion.MZConnent.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponseJson> login(@Valid @RequestBody LoginMemberRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        TokenResponse tokenResponse = loginService.loginUser(request.getEmail(), request.getPassword());

        log.info("token 발행: {}", tokenResponse.toString());

        return ResponseEntity.ok(new ApiResponseJson(
                HttpStatus.OK, null, tokenResponse
        ));
    }

    @PostMapping("/user/logout")
    public ResponseEntity<ApiResponseJson> logout(@AuthenticationPrincipal UserPrinciple userPrinciple, @RequestHeader("Authorization") String authHeader) {
        String email = userPrinciple.getEmail();

        log.info("로그아웃 이메일: {}", email);

        // Bearer 를 문자열에서 제외하기 위해 substring을 사용
        loginService.logoout(authHeader.substring(7), email);

        return ResponseEntity.ok(new ApiResponseJson(HttpStatus.OK, "로그아웃 성공"));
    }

    @GetMapping("/user/info")
    public ResponseEntity<ApiResponseJson> getMemberInfo(@AuthenticationPrincipal UserPrinciple userPrinciple){
        String email = userPrinciple.getEmail();
        MemberInfoDto memberInfoDto = loginService.getMemberInfo(email);

        return ResponseEntity.ok(new ApiResponseJson(HttpStatus.OK, null, memberInfoDto));
    }
}
