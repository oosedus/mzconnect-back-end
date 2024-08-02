package likelion.MZConnent.dto.club.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SendChatResponse {
    private Long chatId;
    private LocalDateTime createdDate;
    private String content;
    private Long memberId;
    private Long clubId;

    @Builder
    public SendChatResponse(Long chatId, LocalDateTime createdDate, String content, Long memberId, Long clubId) {
        this.chatId = chatId;
        this.createdDate = createdDate;
        this.content = content;
        this.memberId = memberId;
        this.clubId = clubId;
    }
}
