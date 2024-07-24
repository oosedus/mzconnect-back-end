package likelion.MZConnent.api.culture;

import likelion.MZConnent.dto.culture.CultureCategoryResponse;
import likelion.MZConnent.service.culture.CultureCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CultureController {
    private final CultureCategoryService cultureCategoryService;

    // 전체 문화 카테고리 조회
    @GetMapping("/api/categories/culture")
    public CultureCategoryResponse getAllCultureCategories() {
        CultureCategoryResponse all = cultureCategoryService.getAllCultureCategories();
        log.info("전체 문화 카테고리: {}", all.getCultureCategories());
        return all;
    }
}
