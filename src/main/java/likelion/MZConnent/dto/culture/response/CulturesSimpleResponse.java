package likelion.MZConnent.dto.culture.response;

import likelion.MZConnent.domain.culture.Culture;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CulturesSimpleResponse {
    private Long cultureId;
    private String name;
    private String summary;
    private String cultureImageUrl;
    private String cultureCategoryName;
    private String regionName;
    private int interestCount;
    private int clubCount;

    @Builder
    public CulturesSimpleResponse(Culture culture) {
        this.cultureId = culture.getCultureId();
        this.name = culture.getName();
        this.summary = culture.getSummary();
        this.cultureImageUrl = culture.getCultureImageUrl();
        this.cultureCategoryName = culture.getCultureCategory().getName();
        this.regionName = culture.getRegion().getName();
        this.interestCount = culture.getInterestCount();
        this.clubCount = culture.getClubCount();
    }
}
