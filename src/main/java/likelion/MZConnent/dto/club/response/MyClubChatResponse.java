package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.ClubRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyClubChatResponse {
    private Long clubId;
    private Long memberId;
    private String title;
    private LocalDate meetingDate;
    private String content;
    private String cultureName;
    private Long leaderId;
    private List<ChatDto> chats;

    @Builder
    public MyClubChatResponse(Long clubId, Long memberId, String title, LocalDate meetingDate, String content, String cultureName, Long leaderId, List<ChatDto> chats) {
        this.clubId = clubId;
        this.memberId = memberId;
        this.title = title;
        this.meetingDate = meetingDate;
        this.content = content;
        this.cultureName = cultureName;
        this.leaderId = leaderId;
        this.chats = chats;
    }

    @Getter
    @NoArgsConstructor
    public static class ChatDto {
        private Long chatId;
        private LocalDateTime createdDate;
        private String content;
        private Long memberId;
        private String userName;
        private String memberProfileUrl;
        private ClubRole role;

        @Builder
        public ChatDto(Long chatId, LocalDateTime createdDate, String content, Long memberId, String userName, String memberProfileUrl, ClubRole role) {
            this.chatId = chatId;
            this.createdDate = createdDate;
            this.content = content;
            this.memberId = memberId;
            this.userName = userName;
            this.memberProfileUrl = memberProfileUrl;
            this.role = role;
        }
    }
}
