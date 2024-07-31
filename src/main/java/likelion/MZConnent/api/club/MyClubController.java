package likelion.MZConnent.api.club;

import likelion.MZConnent.dto.club.request.EvaluateMemberRequest;
import likelion.MZConnent.dto.club.request.UpdateClubInfoRequest;
import likelion.MZConnent.dto.club.response.*;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.club.MyClubService;
import likelion.MZConnent.service.club.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/api/clubs/{clubId}/members/{evaluateeId}/rate/count")
    public ResponseEntity<MemberRateResponse> getMemberRateAndCount(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId, @PathVariable Long evaluateeId) {
        MemberRateResponse response = rateService.getMemberRateCountAndCount(userPrinciple.getEmail(), clubId, evaluateeId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/clubs/{clubId}/close")
    public ResponseEntity closeClub(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId) {
        myClubService.closeClub(userPrinciple.getEmail(), clubId);
        return ResponseEntity.ok(Map.of("messege","모임 마감 성공"));
    }

    @DeleteMapping("/api/clubs/{clubId}/members/{memberId}")
    public ResponseEntity deleteMember(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId, @PathVariable Long memberId) {
        myClubService.deleteClubMember(userPrinciple.getEmail(), clubId, memberId);
        return ResponseEntity.ok(Map.of("messege","멤버 추방 성공"));
    }

    @PostMapping("/api/clubs/{clubId}/leave")
    public ResponseEntity leaveClub(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId) {
        myClubService.leaveClub(userPrinciple.getEmail(), clubId);
        return ResponseEntity.ok(Map.of("messege","모임 탈퇴 성공"));
    }

    @PutMapping("/api/clubs/{clubId}")
    public ResponseEntity<UpdateClubInfoResponse> updateClubInfo(@AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable Long clubId, @RequestBody UpdateClubInfoRequest request){
        UpdateClubInfoResponse response = myClubService.updateClubInfo(userPrinciple.getEmail(), clubId, request);
        return ResponseEntity.ok(response);
    }
}
