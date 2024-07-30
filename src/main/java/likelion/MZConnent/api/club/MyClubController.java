package likelion.MZConnent.api.club;

import likelion.MZConnent.dto.club.request.EvaluateMemberRequest;
import likelion.MZConnent.dto.club.response.EvaluateMemberResponse;
import likelion.MZConnent.dto.club.response.MyClubDetailResponse;
import likelion.MZConnent.dto.club.response.MyClubSimpleResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.club.MyClubService;
import likelion.MZConnent.service.club.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MyClubController {
    private final MyClubService myClubService;
    private final RateService rateService;

    @GetMapping("/api/users/clubs")
    public ResponseEntity<MyClubSimpleResponse> getMyClubs(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        MyClubSimpleResponse response = myClubService.getMyClubs(userPrinciple.getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/users/clubs/{clubId}")
    public ResponseEntity<MyClubDetailResponse> getMyClubDetail(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId) {
        MyClubDetailResponse response = myClubService.getMyClubDetail(userPrinciple.getEmail(), clubId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/api/clubs/{clubId}/members/{evaluateeId}/rate")
    public ResponseEntity<EvaluateMemberResponse> evaluateMember(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId, @PathVariable Long evaluateeId, @RequestBody EvaluateMemberRequest request) {
        EvaluateMemberResponse response = rateService.evaluateMember(userPrinciple.getEmail(), clubId, evaluateeId, request);
        return ResponseEntity.ok(response);
    }

}
