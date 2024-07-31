package likelion.MZConnent.dto.club.request;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.GenderRestriction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateClubInfoRequest {
    private String title;
    private LocalDate meetingDate;
    private String content;
    private GenderRestriction genderRestriction;
    private AgeRestriction ageRestriction;
    private Integer maxParticipant;

}
