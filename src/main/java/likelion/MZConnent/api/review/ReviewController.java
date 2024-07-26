package likelion.MZConnent.api.review;

import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.service.review.ReviewService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/api/reviews")
    ResponseEntity<PageContentResponse> getReviewSimpleList(@RequestParam(required = false, defaultValue = "", value = "keyword") String keyword, @RequestParam(required = false, defaultValue = "0", value = "page") int page ) {

        PageContentResponse<ReviewsSimpleResponse> response = reviewService.getReviewsSimpleList(keyword, page);
        return ResponseEntity.ok(response);
     }
}
