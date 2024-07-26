package likelion.MZConnent.api.culture;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.culture.CultureCategory;
import likelion.MZConnent.dto.culture.request.CreateCultureRequest;
import likelion.MZConnent.repository.culture.CultureCategoryRepository;
import likelion.MZConnent.repository.culture.CultureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;

// 문화 추가용
@RestController
@Slf4j
@RequiredArgsConstructor
public class CreateCultureController {
    private final CultureRepository cultureRepository;
    private final CultureCategoryRepository cultureCategoryRepository;

    @PostMapping("/api/cultures")
    public ResponseEntity<Culture> createCulture(@RequestBody CreateCultureRequest request) {

        log.info("culture request: {}", request);

        CultureCategory cultureCategory = cultureCategoryRepository.findById(request.getCultureCategoryId()).orElseThrow(()-> new IllegalArgumentException("해당하는 카테고리가 존재하지 않습니다."));

        Culture newCulture = Culture.builder()
                .name(request.getName())
                .summary(request.getSummary())
                .content(request.getContent())
                .cultureImageUrl(request
                        .getCultureImageUrl())
                .recommendedMember(request.getRecommendedMember())
                .cultureCategory(cultureCategory)
                .build();

        Culture savedCulture = cultureRepository.save(newCulture);

        return ResponseEntity.ok(savedCulture);
    }
}
