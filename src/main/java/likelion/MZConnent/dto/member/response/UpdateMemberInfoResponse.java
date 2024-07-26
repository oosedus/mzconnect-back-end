package likelion.MZConnent.dto.member.response;

import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.culture.CultureCategoryDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMemberInfoResponse {
    private Long memberId;
    private String username;
    private String instagramId;
    private String facebookId;
    private List<CultureCategoryDto> selfIntroductions;

    @Builder
    public UpdateMemberInfoResponse(Member member) {
        this.memberId = member.getId();
        this.username = member.getUsername();
        this.selfIntroductions = member.getSelfIntroductions().stream()
                .map(selfIntroduction->new CultureCategoryDto(selfIntroduction.getCultureCategory()))
                .collect(Collectors.toList());
        this.instagramId = member.getInstagramId();
        this.facebookId = member.getFacebookId();
    }
}
