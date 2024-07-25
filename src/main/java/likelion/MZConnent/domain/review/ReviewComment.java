package likelion.MZConnent.domain.review;

import jakarta.persistence.*;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ReviewComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "reviewId", nullable = false)
    private Review review;

    @Builder
    public ReviewComment(String content, LocalDateTime createdDate, Member member, Review review) {
        this.content = content;
        this.createdDate = createdDate;
        this.member = member;
        this.review = review;
    }
}
