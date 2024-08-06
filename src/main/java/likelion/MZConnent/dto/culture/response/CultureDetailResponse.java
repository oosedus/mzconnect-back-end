package likelion.MZConnent.dto.culture.response;

import likelion.MZConnent.domain.club.AgeRestriction;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.club.GenderRestriction;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.dto.club.response.ClubSimpleResponse;
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
    private List<ClubSimpleResponse> clubs;
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
                )).map(ClubSimpleResponse::new).collect(Collectors.toList());
        this.reviews = culture.getReviews().stream().map(
                review -> ReviewsSimpleResponse.builder()
                        .review(review).build()
        ).collect(Collectors.toList());
    }
}
