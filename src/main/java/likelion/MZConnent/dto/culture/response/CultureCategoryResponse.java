package likelion.MZConnent.dto.culture.response;

import likelion.MZConnent.domain.culture.CultureCategory;
import likelion.MZConnent.dto.culture.CultureCategoryDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CultureCategoryResponse {
    List<CultureCategoryDto> cultureCategories = new ArrayList<>();

    public CultureCategoryResponse(List<CultureCategory> cultureCategory) {
        this.cultureCategories = cultureCategory.stream()
                .map(CultureCategoryDto::new)
                .collect(Collectors.toList());
    }


}
