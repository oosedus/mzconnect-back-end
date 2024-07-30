package likelion.MZConnent.dto.review.response;

import likelion.MZConnent.domain.review.ReviewComment;
import likelion.MZConnent.dto.member.MemberProfileDto;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveCommentResponse {
    private Long commentId;
    private Long reviewId;
    private MemberProfileDto commenter;
    private String content;


    @Builder
    public SaveCommentResponse(ReviewComment comment) {
        this.commentId = comment.getCommentId();
        this.reviewId = comment.getReview().getReviewId();
        this.commenter = MemberProfileDto.builder()
                .member(comment.getMember()).build();
        this.content = comment.getContent();
    }
}
