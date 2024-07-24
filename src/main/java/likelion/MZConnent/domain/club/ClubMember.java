package likelion.MZConnent.domain.club;

import jakarta.persistence.*;
import likelion.MZConnent.domain.manner.Manner;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMemberId;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClubRole clubRole;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "clubId", nullable = false)
    private Club club;

    @OneToMany(mappedBy = "clubMember")
    private List<Manner> manners;

    @Builder
    public ClubMember(ClubRole clubRole, Member member, Club club) {
        this.clubRole = clubRole;
        this.member = member;
        this.club = club;
    }
}
