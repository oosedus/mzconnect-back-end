package likelion.MZConnent.dto.member;

import likelion.MZConnent.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberProfileDto {
    private Long userId;
    private String username;
    private String profileImageUrl = ""; // TODO: 기본 이미지로 초기화

    public MemberProfileDto(Member member) {
        this.userId = member.getId();
        this.username = member.getUsername();
        this.profileImageUrl = member.getProfileImageUrl();
    }
}
