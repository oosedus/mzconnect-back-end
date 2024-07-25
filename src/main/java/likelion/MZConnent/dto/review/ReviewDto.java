package likelion.MZConnent.dto.review;

import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.member.MemberProfileDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDto {
    private Long reviewId;
    private MemberProfileDto reviewer;
    private String title;
    private String reviewImageUrl;
    private String cultureName;
    private LocalDateTime createDate;
    private int likeCount;
    private int commentCount;

    public ReviewDto(Review review) {
        reviewId = review.getReviewId();
        reviewer = new MemberProfileDto(review.getMember());
        title = review.getTitle();
        reviewImageUrl = review.getReviewImageUrl1();
        cultureName =  review.getCulture().getName();
        createDate = review.getCreateDate();
        likeCount = review.getLikeCount();
        commentCount = review.getCommentCount();
    }
}
