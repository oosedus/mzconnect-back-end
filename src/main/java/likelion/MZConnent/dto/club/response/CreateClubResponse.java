package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.GenderRestriction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CreateClubResponse {
    private Long cultureId;
    private Long regionId;
    private String title;
    private LocalDate meetingDate;
    private LocalDateTime createdDate;
    private int maxParticipant;
    private String content;
    private GenderRestriction genderRestriction;
    private AgeRestriction ageRestriction;
    private String status;

    @Builder
    public CreateClubResponse(Long cultureId, Long regionId, String title, LocalDate meetingDate, LocalDateTime createdDate, int maxParticipant, String content, GenderRestriction genderRestriction, AgeRestriction ageRestriction, String status) {
        this.cultureId = cultureId;
        this.regionId = regionId;
        this.title = title;
        this.meetingDate = meetingDate;
        this.createdDate = createdDate;
        this.maxParticipant = maxParticipant;
        this.content = content;
        this.genderRestriction = genderRestriction;
        this.ageRestriction = ageRestriction;
        this.status = status;
    }
}
