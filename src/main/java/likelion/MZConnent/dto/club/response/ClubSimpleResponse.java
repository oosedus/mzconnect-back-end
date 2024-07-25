package likelion.MZConnent.dto.club.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClubSimpleResponse {
    private Long clubId;
    private String title;
    private Long regionId;
    private String regionName;
    private Long cultureCategoryId;
    private String cultureName;
    private String leaderProfileImage;
    private LocalDate meetingDate;
    private LocalDateTime createdDate;
    private String genderRestriction;
    private String ageRestriction;
    private int currentParticipant;
    private int maxParticipant;

    @Builder
    public ClubSimpleResponse(Long clubId, String title, Long regionId, String regionName, Long cultureCategoryId,
                              String cultureName, String leaderProfileImage, LocalDate meetingDate,
                              LocalDateTime createdDate, String genderRestriction, String ageRestriction,
                              int currentParticipant, int maxParticipant) {
        this.clubId = clubId;
        this.title = title;
        this.regionId = regionId;
        this.regionName = regionName;
        this.cultureCategoryId = cultureCategoryId;
        this.cultureName = cultureName;
        this.leaderProfileImage = leaderProfileImage;
        this.meetingDate = meetingDate;
        this.createdDate = createdDate;
        this.genderRestriction = genderRestriction;
        this.ageRestriction = ageRestriction;
        this.currentParticipant = currentParticipant;
        this.maxParticipant = maxParticipant;
    }
}
