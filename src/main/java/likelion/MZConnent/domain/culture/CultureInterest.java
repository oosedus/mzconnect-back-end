package likelion.MZConnent.domain.culture;

import jakarta.persistence.*;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "culture_interests")
public class CultureInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cultureInterestId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cultureId", nullable = false)
    private Culture culture;

    @Builder
    public CultureInterest(Member member, Culture culture) {
        this.member = member;
        this.culture = culture;
    }
}
