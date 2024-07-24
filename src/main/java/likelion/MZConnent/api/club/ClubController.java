package likelion.MZConnent.api.club;

import jakarta.validation.Valid;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.request.CreateClubRequest;
import likelion.MZConnent.dto.club.response.CreateClubResponse;
import likelion.MZConnent.dto.member.MemberInfoDto;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.repository.member.MemberRepository;
import likelion.MZConnent.service.club.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;
    private final MemberRepository memberRepository;

    @PostMapping("/api/clubs")
    public ResponseEntity<CreateClubResponse> createClub(@Valid @RequestBody CreateClubRequest request, @AuthenticationPrincipal UserPrinciple userPrinciple, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new IllegalArgumentException(errorMessage);
        }

        String email = userPrinciple.getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        CreateClubResponse response = clubService.createClub(request, member);
        return ResponseEntity.ok(response);
    }
}
