package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.club.GenderRestriction;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Slf4j
@ToString
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
    private GenderRestriction genderRestriction;
    private AgeRestriction ageRestriction;
    private int currentParticipant;
    private int maxParticipant;

    @Builder
    public ClubSimpleResponse(Long clubId, String title, Long regionId, String regionName, Long cultureCategoryId,
                              String cultureName, String leaderProfileImage, LocalDate meetingDate,
                              LocalDateTime createdDate, GenderRestriction genderRestriction, AgeRestriction ageRestriction,
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

    public ClubSimpleResponse(Club club) {
        this.clubId = club.getClubId();
        this.title = club.getTitle();
        this.regionId = club.getRegion().getRegionId();
        this.regionName = club.getRegion().getName();
        this.cultureCategoryId = club.getCulture().getCultureId();
        this.cultureName =  club.getCulture().getName();
        this.leaderProfileImage = club.getClubMembers().stream().filter(clubMember -> clubMember.getClubRole().equals(ClubRole.LEADER)).findFirst().orElseThrow(()-> {
            log.info("모임장이 존재하지 않음.");
            return new IllegalArgumentException("모임장이 존재하지 않습니다.");
        }).getMember().getProfileImageUrl();
        this.meetingDate = club.getMeetingDate();
        this.createdDate = club.getCreatedDate();
        this.genderRestriction = club.getGenderRestriction();
        this.ageRestriction = club.getAgeRestriction();
        this.currentParticipant = club.getCurrentParticipant();
        this.maxParticipant = club.getMaxParticipant();
    }
}
