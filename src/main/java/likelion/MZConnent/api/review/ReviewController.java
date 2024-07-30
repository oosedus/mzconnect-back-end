package likelion.MZConnent.api.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.request.SaveReviewRequest;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.dto.review.response.SaveReviewResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

     @PostMapping(value="/api/reviews/culture/{cultureId}", consumes = {"multipart/form-data"})
     public ResponseEntity<SaveReviewResponse> saveReview(
             @AuthenticationPrincipal UserPrinciple userPrinciple,
             @RequestPart("info") String info,
             @RequestPart(value = "reviewImage1", required = true) MultipartFile reviewImage1,
             @RequestPart(value = "reviewImage2", required = false) MultipartFile reviewImage2,
             @RequestPart(value = "reviewImage3", required = false) MultipartFile reviewImage3,
             @RequestPart(value = "reviewImage4", required = false) MultipartFile reviewImage4,
             @PathVariable Long cultureId) throws JsonProcessingException {

         try {
             ObjectMapper objectMapper = new ObjectMapper();
             SaveReviewRequest request = objectMapper.readValue(info, SaveReviewRequest.class);

             List<MultipartFile> images = new ArrayList<>();
             if (reviewImage1 != null) images.add(reviewImage1);
             if (reviewImage2 != null) images.add(reviewImage2);
             if (reviewImage3 != null) images.add(reviewImage3);
             if (reviewImage4 != null) images.add(reviewImage4);

             SaveReviewResponse response = reviewService.createReview(userPrinciple.getEmail(), request, images, cultureId);
             return ResponseEntity.ok(response);
         } catch (IOException e) {
                log.error("리뷰 저장 실패", e);
             return ResponseEntity.status(500).body(null);
         }
     }

    // 후기 좋아요 토글
    @PutMapping("/api/reviews/{reviewId}/likes")
    public ResponseEntity<Map<String, String>> toggleReviewLike(@PathVariable("reviewId") Long reviewId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        boolean status = reviewService.toggleReviewLike(userPrinciple.getEmail(), reviewId);

        if (status) {
            return ResponseEntity.ok(Map.of("message", "후기 좋아요 추가 성공"));
        } else {
            return ResponseEntity.ok(Map.of("message", "후기 좋아요 삭제 성공"));
        }
    }
}
