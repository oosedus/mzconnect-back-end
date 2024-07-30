package likelion.MZConnent.dto.club.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class MyClubSimpleResponse {
    private List<MyClubSimpleDto> myClubs;

    @Builder
    public MyClubSimpleResponse(List<MyClubSimpleDto> myClubs) {
        this.myClubs = myClubs;
    }

    @Getter
    @NoArgsConstructor
    public static class MyClubSimpleDto {
        private Long clubId;
        private String title;
        private String cultureName;
        private LocalDate meetingDate;
        private int currentParticipant;
        private int maxParticipant;

        @Builder
        public MyClubSimpleDto(Long clubId, String title, String cultureName, LocalDate meetingDate, int currentParticipant, int maxParticipant) {
            this.clubId = clubId;
            this.title = title;
            this.cultureName = cultureName;
            this.meetingDate = meetingDate;
            this.currentParticipant = currentParticipant;
            this.maxParticipant = maxParticipant;
        }

    }
}
