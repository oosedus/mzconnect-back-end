package likelion.MZConnent.dto.member.response;

import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.culture.CultureCategoryDto;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberInfoResponse {
    private Long memberId;
    private String email;
    private String username;
    private Gender gender;
    private Age age;
    private String instagramId;
    private String facebookId;
    private String profileImageUrl;
    private BigDecimal averageMannersScore;
    private List<CultureCategoryDto> selfIntroductions = new ArrayList<>();
    private List<ReviewsSimpleResponse> reviews = new ArrayList<>();


    public MemberInfoResponse(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.gender = member.getGender();
        this.age = member.getAge();
        this.instagramId = member.getInstagramId();
        this.facebookId = member.getFacebookId();
        this.profileImageUrl = member.getProfileImageUrl();
        this.averageMannersScore = member.getAverageMannersScore();
        this.selfIntroductions = member.getSelfIntroductions().stream()
                .map((selfIntroduction)-> new CultureCategoryDto(selfIntroduction.getCultureCategory()))
                .collect(Collectors.toList());
        this.reviews = member.getReviews().stream()
                .map(ReviewsSimpleResponse::new)
                .collect(Collectors.toList());
    }
}
