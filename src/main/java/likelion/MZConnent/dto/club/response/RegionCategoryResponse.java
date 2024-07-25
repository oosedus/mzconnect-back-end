package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.RegionCategory;
import likelion.MZConnent.dto.club.RegionCategoryDto;
import likelion.MZConnent.dto.culture.CultureCategoryDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionCategoryResponse {
    List<RegionCategoryDto> regionCategories = new ArrayList<>();

    public RegionCategoryResponse(List<RegionCategory> regionCategory) {
        this.regionCategories = regionCategory.stream()
                .map(RegionCategoryDto::new)
                .collect(Collectors.toList());
    }
}
