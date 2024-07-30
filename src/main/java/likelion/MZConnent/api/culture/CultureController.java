package likelion.MZConnent.api.culture;

import likelion.MZConnent.dto.culture.response.CultureCategoryResponse;
import likelion.MZConnent.dto.culture.response.CultureDetailResponse;
import likelion.MZConnent.dto.culture.response.CulturesSimpleResponse;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.culture.CultureCategoryService;
import likelion.MZConnent.service.culture.CultureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CultureController {
    private final CultureCategoryService cultureCategoryService;

    private final CultureService cultureService;

    // 전체 문화 카테고리 조회
    @GetMapping("/api/categories/culture")
    public ResponseEntity<CultureCategoryResponse> getAllCultureCategories() {
        CultureCategoryResponse all = cultureCategoryService.getAllCultureCategories();
        log.info("전체 문화 카테고리: {}", all.getCultureCategories());
        return ResponseEntity.ok(all);
    }

    // 전체 문화 간단 조회
    @GetMapping("/api/cultures")
    public ResponseEntity<PageContentResponse> getCulturesSimpleList(@RequestParam(required = false, defaultValue = "0", value = "category") Long category, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {

        PageContentResponse<CulturesSimpleResponse> response = cultureService.getCulturesSimpleList(category, page);
        return ResponseEntity.ok(response);
    }


    // 나의 관심 문화 간단 조회
    @GetMapping("/api/cultures/interest")
    public ResponseEntity<PageContentResponse> getMyInterestCulturesSimpleList(@RequestParam(required = false, defaultValue = "0", value = "category") Long category, @RequestParam(required = false, defaultValue = "0", value = "page") int page, @AuthenticationPrincipal UserPrinciple userPrinciple) {

        PageContentResponse<CulturesSimpleResponse> response = cultureService.getMyIntersetCulturesSimpleList(userPrinciple.getEmail(), category, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/cultures/{cultureId}")
    public ResponseEntity<CultureDetailResponse> getCultureDetailInfo(@PathVariable("cultureId") Long cultureId) {
        CultureDetailResponse response = cultureService.getCultureDetailInfo(cultureId);

        log.info("문화 정보 조회 성공: {}", response.getCultureId());

        return ResponseEntity.ok(response);
    }

    // 문화 관심 토글
    @PostMapping("/api/cultures/{cultureId}/interests")
    public ResponseEntity<Map<String, String>> addCultureInterest(@PathVariable("cultureId") Long cultureId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        boolean status = cultureService.toggleCultureInterest(userPrinciple.getEmail(), cultureId);

        if (status) {
            return ResponseEntity.ok(Map.of("message", "관심 문화 추가 성공"));
        } else {
            return ResponseEntity.ok(Map.of("message", "관심 문화 삭제 성공"));
        }
    }
}
