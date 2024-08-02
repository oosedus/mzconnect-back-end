package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.GenderRestriction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateClubInfoResponse {
    private Long clubId;
    private String title;
    private LocalDate meetingDate;
    private String content;
    private GenderRestriction genderRestriction;
    private AgeRestriction ageRestriction;
    private int maxParticipant;
    private String cultureName;

    @Builder
    public UpdateClubInfoResponse(Long clubId, String title, LocalDate meetingDate, String content, GenderRestriction genderRestriction, AgeRestriction ageRestriction, int maxParticipant, String cultureName){
        this.clubId = clubId;
        this.title = title;
        this.meetingDate = meetingDate;
        this.content = content;
        this.genderRestriction = genderRestriction;
        this.ageRestriction = ageRestriction;
        this.maxParticipant = maxParticipant;
        this.cultureName = cultureName;
    }
}
