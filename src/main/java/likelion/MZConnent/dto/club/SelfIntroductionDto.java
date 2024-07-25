package likelion.MZConnent.dto.club;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SelfIntroductionDto {
    private Long cultureCategoryId;
    private String name;

    @Builder
    public SelfIntroductionDto(Long cultureCategoryId, String name) {
        this.cultureCategoryId = cultureCategoryId;
        this.name = name;
    }
}
