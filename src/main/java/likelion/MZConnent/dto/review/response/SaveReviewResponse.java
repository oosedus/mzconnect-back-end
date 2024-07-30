package likelion.MZConnent.dto.review.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class SaveReviewResponse {
    private Long reviewId;
    private ReviewerDto reviewer;
    private CultureDto culture;
    private String title;
    private String reviewImageUrl1;
    private String reviewImageUrl2;
    private String reviewImageUrl3;
    private String reviewImageUrl4;
    private LocalDateTime createdDate;
    private int likeCount;

    @Builder
    public SaveReviewResponse(Long reviewId, ReviewerDto reviewer, CultureDto culture, String title, String reviewImageUrl1, String reviewImageUrl2, String reviewImageUrl3, String reviewImageUrl4, LocalDateTime createdDate, int likeCount) {
        this.reviewId = reviewId;
        this.reviewer = reviewer;
        this.culture = culture;
        this.title = title;
        this.reviewImageUrl1 = reviewImageUrl1;
        this.reviewImageUrl2 = reviewImageUrl2;
        this.reviewImageUrl3 = reviewImageUrl3;
        this.reviewImageUrl4 = reviewImageUrl4;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
    }

    @Getter
    @NoArgsConstructor
    public static class ReviewerDto {
        private Long userId;
        private String username;
        private String profileImage;

        @Builder
        public ReviewerDto(Long userId, String username, String profileImage) {
            this.userId = userId;
            this.username = username;
            this.profileImage = profileImage;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CultureDto {
        private Long cultureId;
        private String cultureName;

        @Builder
        public CultureDto(Long cultureId, String cultureName) {
            this.cultureId = cultureId;
            this.cultureName = cultureName;
        }
    }

}
