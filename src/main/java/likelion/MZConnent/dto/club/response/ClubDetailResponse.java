package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.GenderRestriction;
import likelion.MZConnent.dto.club.LeaderDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class ClubDetailResponse {
    private Long clubId;
    private String title;
    private LocalDate meetingDate;
    private LocalDateTime createdDate;
    private String content;
    private GenderRestriction genderRestriction;
    private AgeRestriction ageRestriction;
    private String cultureImageUrl;
    private String cultureName;
    private String regionName;
    private int currentParticipant;
    private int maxParticipant;
    private String registrationStatus;
    private List<LeaderDto> leader;

    @Builder
    public ClubDetailResponse(Long clubId, String title, LocalDate meetingDate, LocalDateTime createdDate, String content,
                              GenderRestriction genderRestriction, AgeRestriction ageRestriction, String cultureImageUrl,
                              String cultureName, String regionName, int currentParticipant, int maxParticipant,
                              String registrationStatus, List<LeaderDto> leader) {
        this.clubId = clubId;
        this.title = title;
        this.meetingDate = meetingDate;
        this.createdDate = createdDate;
        this.content = content;
        this.genderRestriction = genderRestriction;
        this.ageRestriction = ageRestriction;
        this.cultureImageUrl = cultureImageUrl;
        this.cultureName = cultureName;
        this.regionName = regionName;
        this.currentParticipant = currentParticipant;
        this.maxParticipant = maxParticipant;
        this.registrationStatus = registrationStatus;
        this.leader = leader;
    }

}
