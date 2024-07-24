package likelion.MZConnent.domain.club;

import jakarta.persistence.*;
import likelion.MZConnent.domain.chat.Chat;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate meetingDate;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderRestriction genderRestriction;

    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;

    @Column(nullable = false)
    private int currentParticipant;

    @Column(nullable = false)
    private int maxParticipant;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cultureId", nullable = false)
    private Culture culture;

    @ManyToOne
    @JoinColumn(name = "regionId", nullable = false)
    private RegionCategory region;

    @OneToMany(mappedBy = "club")
    private List<Chat> chats;

    @OneToMany(mappedBy = "club")
    private List<ClubMember> clubMembers;


    @Builder
    public Club(String title, LocalDate meetingDate, LocalDateTime createdDate, String content, GenderRestriction genderRestriction, AgeRestriction ageRestriction, int currentParticipant, int maxParticipant, String status, Member member, Culture culture, RegionCategory region) {
        this.title = title;
        this.meetingDate = meetingDate;
        this.createdDate = createdDate;
        this.content = content;
        this.genderRestriction = genderRestriction;
        this.ageRestriction = ageRestriction;
        this.currentParticipant = currentParticipant;
        this.maxParticipant = maxParticipant;
        this.status = status;
        this.member = member;
        this.culture = culture;
        this.region = region;
    }
}
