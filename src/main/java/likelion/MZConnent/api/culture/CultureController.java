package likelion.MZConnent.api.culture;

import likelion.MZConnent.dto.culture.response.CultureCategoryResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    ResponseEntity<PageContentResponse> getCulturesSimpleList(@RequestParam(required = false, defaultValue = "0", value = "category") Long category, @RequestParam(required = false, defaultValue = "0", value = "page") int page ) {

        PageContentResponse<CulturesSimpleResponse> response = cultureService.getCulturesSimpleList(category, page);
        return ResponseEntity.ok(response);
    }


    // 나의 관심 문화 간단 조회
    @GetMapping("/api/cultures/interest")
    ResponseEntity<PageContentResponse> getMyInterestCulturesSimpleList(@RequestParam(required = false, defaultValue = "0", value = "category") Long category, @RequestParam(required = false, defaultValue = "0", value = "page") int page, @AuthenticationPrincipal UserPrinciple userPrinciple) {

        PageContentResponse<CulturesSimpleResponse> response = cultureService.getMyIntersetCulturesSimpleList(userPrinciple.getEmail(), category, page);
        return ResponseEntity.ok(response);
    }
}
