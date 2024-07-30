package likelion.MZConnent.dto.club.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class MemberRateResponse {
    private int rateCount;
    private String userName;
    private BigDecimal averageMannersScore;
    private String profileImageUrl;

    @Builder
    public MemberRateResponse(int rateCount, String userName, BigDecimal averageMannersScore, String profileImageUrl) {
        this.rateCount = rateCount;
        this.userName = userName;
        this.averageMannersScore = averageMannersScore;
        this.profileImageUrl = profileImageUrl;
    }
}
