package likelion.MZConnent.dto.club;

import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LeaderDto {
    private String username;
    private Age age;
    private Gender gender;
    private String profileImageUrl;
    private List<SelfIntroductionDto> selfIntroductions;

    @Builder
    public LeaderDto(String username,Age age,Gender gender, String profileImageUrl, List<SelfIntroductionDto> selfIntroductions) {
        this.username = username;
        this.age=age;
        this.gender=gender;
        this.profileImageUrl = profileImageUrl;
        this.selfIntroductions = selfIntroductions;
    }
}
