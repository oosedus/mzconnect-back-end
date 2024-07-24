package likelion.MZConnent.domain.chat;

import jakarta.persistence.*;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "clubId", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @Builder
    public Chat(LocalDateTime createdDate, String content, Club club, Member member) {
        this.createdDate = createdDate;
        this.content = content;
        this.club = club;
        this.member = member;
    }
}
