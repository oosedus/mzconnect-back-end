package likelion.MZConnent.dto.club.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubSimpleRequest {
    private Long cultureId;
    private Long regionId;

    @Builder
    public ClubSimpleRequest(Long cultureId, Long regionId) {
        this.cultureId = cultureId;
        this.regionId = regionId;
    }
}
