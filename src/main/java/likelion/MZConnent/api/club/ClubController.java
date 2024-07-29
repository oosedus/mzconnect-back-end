package likelion.MZConnent.api.club;

import jakarta.validation.Valid;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.request.ClubSimpleRequest;
import likelion.MZConnent.dto.club.request.CreateClubRequest;
import likelion.MZConnent.dto.club.response.*;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.repository.member.MemberRepository;
import likelion.MZConnent.service.club.ClubInfoService;
import likelion.MZConnent.service.club.ClubService;
import likelion.MZConnent.service.club.RegionCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;
    private final ClubInfoService clubInfoService;
    private final MemberRepository memberRepository;
    private final RegionCategoryService regionCategoryService;

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

    @GetMapping("/api/categories/region")
    public ResponseEntity<RegionCategoryResponse> getAllRegionCategories() {
        RegionCategoryResponse all = regionCategoryService.getAllRegionCategories();
        log.info("전체 지역 카테고리: {}", all.getRegionCategories());
        return ResponseEntity.ok(all);
    }

    @PostMapping("/api/clubs/{clubId}/join")
    public ResponseEntity joinClub(@PathVariable Long clubId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        String email = userPrinciple.getEmail();
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        clubService.joinClub(clubId, member);
        return ResponseEntity.ok(Map.of("message","모임 가입 성공"));
    }

    @GetMapping("/api/clubs/{clubId}")
    public ResponseEntity<ClubDetailResponse> getClubDetail(@PathVariable Long clubId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        ClubDetailResponse clubDetail;
        if (userPrinciple == null) {
            clubDetail = clubInfoService.getClubDetail(clubId);
        } else {
            Member member = memberRepository.findByEmail(userPrinciple.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
            clubDetail = clubInfoService.getClubDetail(clubId, member);
        }

        return ResponseEntity.ok(clubDetail);
    }

    @GetMapping("/api/clubs/list")
    public ResponseEntity<PageContentResponse<ClubSimpleResponse>> getClubList(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "sort", defaultValue = "createdDate") String sortWay,
            @RequestBody ClubSimpleRequest request) {

        Pageable pageable;
        if(sortWay.equals("meetingDate")){
            pageable = PageRequest.of(pageNumber, 6, Sort.by(sortWay).ascending());
        }
        else {
            pageable = PageRequest.of(pageNumber, 6, Sort.by(sortWay).descending());
        }

        PageContentResponse<ClubSimpleResponse> clubList = clubInfoService.getClubList(request, pageable);
        return ResponseEntity.ok(clubList);
    }
}
