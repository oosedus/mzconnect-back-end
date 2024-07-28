package likelion.MZConnent.dto.review.response;


import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.member.MemberProfileDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class ReviewsSimpleResponse {
    private Long reviewId;
    private MemberProfileDto reviewer;
    private String title;
    private String reviewImageUrl;
    private String cultureName;
    private LocalDateTime createdDate;
    private int likeCount;
    private int commentCount;

    @Builder
    public ReviewsSimpleResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewer = new MemberProfileDto(review.getMember());
        this.title = review.getTitle();
        this.reviewImageUrl = review.getReviewImageUrl1();
        this.cultureName = review.getCulture().getName();
        this.createdDate = review.getCreatedDate();
        this.likeCount = review.getLikeCount();
        this.commentCount = review.getCommentCount();
    }
}