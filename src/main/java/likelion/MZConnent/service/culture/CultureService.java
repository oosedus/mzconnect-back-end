package likelion.MZConnent.service.culture;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.culture.response.CulturesSimpleResponse;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.repository.culture.CultureRepository;
import likelion.MZConnent.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CultureService {
    private final CultureRepository cultureRepository;
    private final int PAGE_SIZE = 6;

    public PageContentResponse<CulturesSimpleResponse> getCulturesSimpleList(Long cultureCategoryId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Culture> cultures = cultureRepository.findAllByCultureCategory(cultureCategoryId, pageable);

        List<CulturesSimpleResponse> cultureResponse = cultures.stream().map(culture -> CulturesSimpleResponse.builder()
                .culture(culture).build()
        ).collect(Collectors.toList());

        return PageContentResponse.<CulturesSimpleResponse>builder()
                .content(cultureResponse)
                .totalPages(cultures.getTotalPages())
                .totalElements(cultures.getTotalElements())
                .size(pageable.getPageSize())
                .build();

    }

    public PageContentResponse<CulturesSimpleResponse> getMyIntersetCulturesSimpleList(String email, Long cultureCategoryId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Culture> cultures = cultureRepository.findByMemberAndCategory(email, cultureCategoryId, pageable);

        List<CulturesSimpleResponse> cultureResponse = cultures.stream().map(culture -> CulturesSimpleResponse.builder()
                .culture(culture).build()
        ).collect(Collectors.toList());

        return PageContentResponse.<CulturesSimpleResponse>builder()
                .content(cultureResponse)
                .totalPages(cultures.getTotalPages())
                .totalElements(cultures.getTotalElements())
                .size(pageable.getPageSize())
                .build();

    }
}
