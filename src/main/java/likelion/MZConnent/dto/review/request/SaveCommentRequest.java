package likelion.MZConnent.dto.review.request;

import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.review.ReviewComment;
import likelion.MZConnent.dto.review.response.SaveCommentResponse;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SaveCommentRequest {
    private Long reviewId;
    private String content;
}
