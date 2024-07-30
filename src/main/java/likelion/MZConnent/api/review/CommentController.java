package likelion.MZConnent.api.review;

import likelion.MZConnent.dto.review.request.SaveCommentRequest;
import likelion.MZConnent.dto.review.response.SaveCommentResponse;
import likelion.MZConnent.jwt.principle.UserPrinciple;
import likelion.MZConnent.service.review.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/reviews/{reviewId}/comments")
    public ResponseEntity<SaveCommentResponse> saveComment(@RequestBody Map<String, String> request, @PathVariable("reviewId") Long reviewId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        SaveCommentResponse comment = commentService.saveComment(userPrinciple.getEmail(), reviewId, request.get("content"));

        log.info("댓글 작성: {}", comment);

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/api/reviews/{reviewId}/comments/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        commentService.deleteComment(userPrinciple.getEmail(), commentId);
        return ResponseEntity.ok(Map.of("message", "댓글 삭제 성공"));
    }

}
