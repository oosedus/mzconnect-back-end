package likelion.MZConnent.dto.main.response;

import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.club.response.ClubSimpleResponse;
import likelion.MZConnent.dto.culture.response.CulturesSimpleResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainInfoResponse {
    private List<CulturesSimpleResponse> popularCultures;
    private List<ReviewsSimpleResponse> popularReviews;
    private List<ClubSimpleResponse> recentClubs;

    @Builder
    public MainInfoResponse(List<Culture> popularCultures, List<Review> popularReviews, List<Club> recentClubs) {
        this.popularCultures = popularCultures.stream().map(culture -> CulturesSimpleResponse.builder().culture(culture).build()).collect(Collectors.toList());
        this.popularReviews = popularReviews.stream().map(review -> ReviewsSimpleResponse.builder().review(review).build()).collect(Collectors.toList());
        this.recentClubs = recentClubs.stream().map(ClubSimpleResponse::new).collect(Collectors.toList());
    }
}
