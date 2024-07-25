package likelion.MZConnent.dto.culture;

import likelion.MZConnent.domain.culture.CultureCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CultureCategoryDto {
    private Long cultureCategoryId;
    private String name;

    public CultureCategoryDto(CultureCategory cultureCategory) {
        this.cultureCategoryId = cultureCategory.getId();
        this.name = cultureCategory.getName();
    }
}
