package likelion.MZConnent.dto.culture.response;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.club.GenderRestriction;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Slf4j
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CultureDetailResponse {
    private Long cultureId;
    private String name;
    private String cultureImageUrl;
    private String regionName;
    private int interestCount;
    private String recommendedMember;
    private String content;
    private List<ClubsSimpleResponse> clubs;
    private List<ReviewsSimpleResponse> reviews;

    @Builder
    public CultureDetailResponse(Culture culture) {
        this.cultureId = culture.getCultureId();
        this.name = culture.getName();
        this.cultureImageUrl = culture.getCultureImageUrl();
        this.regionName = culture.getRegion().getName();
        this.interestCount = culture.getInterestCount();
        this.recommendedMember = culture.getRecommendedMember();
        this.content = culture.getContent();
        this.clubs = culture.getClubs().stream().filter(club ->
            (club.getStatus().equals("OPEN")
        )).map(club ->
                ClubsSimpleResponse.builder()
                        .club(club).build()).collect(Collectors.toList());
        this.reviews = culture.getReviews().stream().map(
                review -> ReviewsSimpleResponse.builder()
                        .review(review).build()
        ).collect(Collectors.toList());
    }


    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static private class ClubsSimpleResponse {
        private Long clubId;
        private String title;
        private String regionName;
        private String cultureName;
        private String leaderProfileImage;
        private LocalDate meetingDate;
        private LocalDateTime createdDate;
        private GenderRestriction genderRestriction;
        private AgeRestriction ageRestriction;
        private int currentParticipant;
        private int maxParticipant;

        @Builder
        public ClubsSimpleResponse(Club club) {
            this.clubId = club.getClubId();
            this.title = club.getTitle();
            this.regionName = club.getRegion().getName();
            this.cultureName = club.getCulture().getName();
            this.leaderProfileImage = club.getClubMembers().stream().filter((member) ->
                    member.getClubRole() == ClubRole.LEADER
            ).findFirst().orElseThrow(() -> {
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
}
