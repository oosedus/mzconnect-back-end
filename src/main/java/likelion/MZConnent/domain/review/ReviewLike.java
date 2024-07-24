package likelion.MZConnent.domain.review;

import jakarta.persistence.*;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review_likes")
public class ReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewLikeId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "reviewId", nullable = false)
    private Review review;

    @Builder
    public ReviewLike(Long reviewLikeId, Member member, Review review) {
        this.reviewLikeId = reviewLikeId;
        this.member = member;
        this.review = review;
    }
}
