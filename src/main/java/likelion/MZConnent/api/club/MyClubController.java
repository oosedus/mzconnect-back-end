package likelion.MZConnent.api.club;

import likelion.MZConnent.dto.club.response.MyClubSimpleResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.club.MyClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

}
