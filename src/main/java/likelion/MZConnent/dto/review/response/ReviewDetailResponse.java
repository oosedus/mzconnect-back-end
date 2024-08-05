package likelion.MZConnent.dto.review.response;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.domain.review.ReviewComment;
import likelion.MZConnent.dto.member.MemberProfileDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewDetailResponse {
    private Long reviewId;
    private MemberProfileDto reviewer;
    private CultureSimpleDto culture;
    private String title;
    private String reviewImageUrl1;
    private String reviewImageUrl2;
    private String reviewImageUrl3;
    private String reviewImageUrl4;
    private LocalDateTime createdDate;
    private int likeCount;
    private List<CommentSimpleDto> comments = new ArrayList<CommentSimpleDto>();

    @Builder
    public ReviewDetailResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewer = MemberProfileDto.builder().member(review.getMember()).build();
        this.culture = CultureSimpleDto.builder().culture(review.getCulture()).build();
        this.title = review.getTitle();
        this.reviewImageUrl1 = review.getReviewImageUrl1();
        this.reviewImageUrl2 = review.getReviewImageUrl2();
        this.reviewImageUrl3 = review.getReviewImageUrl3();
        this.reviewImageUrl4 = review.getReviewImageUrl4();
        this.content = review.getContent();
        this.createdDate = review.getCreatedDate();
        this.likeCount = review.getLikeCount();
        this.comments = review.getReviewComments().stream()
                .map(CommentSimpleDto::new).collect(Collectors.toList());
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class CultureSimpleDto {
        private Long cultureId;
        private String name;

        @Builder
        public CultureSimpleDto(Culture culture) {
            this.cultureId = culture.getCultureId();
            this.name = culture.getName();
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class CommentSimpleDto {
        private Long commentId;
        private MemberProfileDto commenter;
        private String content;

        @Builder
        public CommentSimpleDto(ReviewComment comment) {
            this.commentId = comment.getCommentId();
            this.commenter = MemberProfileDto.builder().member(comment.getMember()).build();
            this.content = comment.getContent();
        }
    }

}
