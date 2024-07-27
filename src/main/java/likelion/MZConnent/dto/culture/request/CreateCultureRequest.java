package likelion.MZConnent.dto.culture.request;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import likelion.MZConnent.domain.culture.CultureCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCultureRequest {
    private String name;

    private String summary;

    private String content;

    private String cultureImageUrl;

    private String recommendedMember;

    private Long cultureCategoryId;

    private Long regionId;
}
