package likelion.MZConnent.dto.club.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.GenderRestriction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateClubRequest {
    private Long cultureId;
    private Long regionId;

    @NotNull
    @Size(min = 2, max = 255, message = "제목은 2자 이상, 255자 이하여야합니다.")
    private String title;

    @NotNull
    private LocalDate meetingDate;

    @NotNull
    private int maxParticipant;

    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderRestriction genderRestriction;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AgeRestriction ageRestriction;
}
