package likelion.MZConnent.api.member;

import jakarta.validation.Valid;
import likelion.MZConnent.dto.member.request.CreateMemberRequest;
import likelion.MZConnent.dto.member.request.LoginMemberRequest;
import likelion.MZConnent.dto.member.response.MemberInfoResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.jwt.token.TokenResponse;
import likelion.MZConnent.service.member.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/api/auth/signup")
    public ResponseEntity register(@Valid @RequestBody CreateMemberRequest request, BindingResult bindingResult) { // @Valid를 통해 검증한 결과는 BindingResult를 통해 받아볼 수 있음

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new IllegalArgumentException(errorMessage);
        }

        Long memberId = loginService.createUser(request);
        log.info("회원가입 성공: {}", memberId);

        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity login(@Valid @RequestBody LoginMemberRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        TokenResponse tokenResponse = loginService.loginUser(request.getEmail(), request.getPassword());

        log.info("token 발행: {}", tokenResponse.toString());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Map<String, String>> logout(@AuthenticationPrincipal UserPrinciple userPrinciple, @RequestHeader("Authorization") String authHeader) {
        String email = userPrinciple.getEmail();

        log.info("로그아웃 이메일: {}", email);

        // Bearer 를 문자열에서 제외하기 위해 substring을 사용
        loginService.logoout(authHeader.substring(7), email);

        return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
    }

    @GetMapping("/api/auth/email")
    public ResponseEntity<Map<String, String>> checkDuplicateEmail(@RequestParam("email") String email) {
        loginService.checkDuplicateEmail(email);
        return ResponseEntity.ok(Map.of("message", "이메일 중복 점검 성공"));
    }

    @GetMapping("/api/auth/username")
    public ResponseEntity<Map<String, String>> checkDuplicateUsername(@RequestParam("username") String username) {
        loginService.checkDuplicateUsername(username);
        return ResponseEntity.ok(Map.of("message", "닉네임 중복 점검 성공"));
    }
}
