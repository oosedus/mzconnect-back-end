package likelion.MZConnent.dto.club;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LeaderDto {
    private String username;
    private String profileImageUrl;
    private List<SelfIntroductionDto> selfIntroductions;

    @Builder
    public LeaderDto(String username, String profileImageUrl, List<SelfIntroductionDto> selfIntroductions) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.selfIntroductions = selfIntroductions;
    }
}
