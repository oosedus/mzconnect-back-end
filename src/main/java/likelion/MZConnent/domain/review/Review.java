package likelion.MZConnent.domain.review;

import jakarta.persistence.*;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String reviewImageUrl1;

    @Column(columnDefinition = "TEXT")
    private String reviewImageUrl2;

    @Column(columnDefinition = "TEXT")
    private String reviewImageUrl3;

    @Column(columnDefinition = "TEXT")
    private String reviewImageUrl4;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int commentCount;

    @ManyToOne
    @JoinColumn(name = "cultureId", nullable = false)
    private Culture culture;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "review")
    private List<ReviewComment> reviewComments;

    @OneToMany(mappedBy = "review")
    private List<ReviewLike> reviewLikes;

    @Builder
    public Review(Long reviewId, String title, String content, String reviewImageUrl1, String reviewImageUrl2, String reviewImageUrl3, String reviewImageUrl4, LocalDateTime createDate, Integer likeCount, Integer commentCount, Culture culture, Member member) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.reviewImageUrl1 = reviewImageUrl1 != null ? reviewImageUrl1 : ""; // 나중에 기본 이미지 URL 추가
        this.reviewImageUrl2 = reviewImageUrl2 != null ? reviewImageUrl2 : "";
        this.reviewImageUrl3 = reviewImageUrl3 != null ? reviewImageUrl3 : "";
        this.reviewImageUrl4 = reviewImageUrl4 != null ? reviewImageUrl4 : "";
        this.createDate = createDate;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.culture = culture;
        this.member = member;
    }
}
