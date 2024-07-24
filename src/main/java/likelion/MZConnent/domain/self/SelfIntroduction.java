package likelion.MZConnent.domain.self;

import jakarta.persistence.*;
import likelion.MZConnent.domain.culture.CultureCategory;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "self_introductions")
public class SelfIntroduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selfIntroductionId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cultureCategoryId", nullable = false)
    private CultureCategory cultureCategory;

    @Builder
    public SelfIntroduction(Long selfIntroductionId, Member member, CultureCategory cultureCategory) {
        this.selfIntroductionId = selfIntroductionId;
        this.member = member;
        this.cultureCategory = cultureCategory;
    }
}