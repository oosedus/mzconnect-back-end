package likelion.MZConnent.api.member;

import likelion.MZConnent.dto.member.response.MemberInfoResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    // 나의 정보 조회
    @GetMapping("/api/user/me")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal UserPrinciple userPrinciple){
        String email = userPrinciple.getEmail();
        MemberInfoResponse memberInfo = memberService.getMemberInfoByEmail(email);

        log.info("멤버 정보 조회 성공: {}", memberInfo.getUsername());
        
        return ResponseEntity.ok(memberInfo);
    }

    // 타유저 정보 조회
    @GetMapping("/api/user/{userId}")
    public ResponseEntity<MemberInfoResponse> getMemberInfo(@PathVariable("userId") Long userId){
        MemberInfoResponse memberInfo = memberService.getMemberInfoById(userId);

        log.info("멤버 정보 조회 성공: {}", memberInfo.getUsername());

        return ResponseEntity.ok(memberInfo);
    }
}
