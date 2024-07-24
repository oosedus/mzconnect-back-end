package likelion.MZConnent.domain.manner;

import jakarta.persistence.*;
import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Manner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mannerId;

    @Column(nullable = false)
    private int score;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "clubMemberId", nullable = false)
    private ClubMember clubMember;

    @Builder
    public Manner(int score, Member member, ClubMember clubMember) {
        this.score = score;
        this.member = member;
        this.clubMember = clubMember;
    }
}