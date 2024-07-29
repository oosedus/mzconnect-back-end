package likelion.MZConnent.api.club;

import likelion.MZConnent.dto.club.response.MyClubDetailResponse;
import likelion.MZConnent.dto.club.response.MyClubSimpleResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.club.MyClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MyClubController {
    private final MyClubService myClubService;

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


}
