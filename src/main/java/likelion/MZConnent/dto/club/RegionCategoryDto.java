package likelion.MZConnent.dto.club;

import likelion.MZConnent.domain.club.RegionCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionCategoryDto {
    private Long regionCategoryId;
    private String name;

    public RegionCategoryDto(RegionCategory regionCategory) {
        this.regionCategoryId = regionCategory.getRegionId();
        this.name = regionCategory.getName();
    }
}
