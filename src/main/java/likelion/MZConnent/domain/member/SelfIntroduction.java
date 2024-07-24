package likelion.MZConnent.domain.member;

import jakarta.persistence.*;
import likelion.MZConnent.domain.culture.CultureCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SelfIntroduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "self_introduction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "culture_category_id")
    private CultureCategory cultureCategory;

    @Builder
    public SelfIntroduction(Member member, CultureCategory cultureCategory) {
        this.member = member;
        this.cultureCategory = cultureCategory;
    }
}

