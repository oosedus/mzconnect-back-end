package likelion.MZConnent.dto.culture;

import likelion.MZConnent.domain.culture.CultureCategory;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Getter
    private static class CultureCategoryDto {
        private Long cultureCategoryId;
        private String name;

        public CultureCategoryDto(CultureCategory cultureCategory) {
            this.cultureCategoryId = cultureCategory.getId();
            this.name = cultureCategory.getName();
        }
    }
}
