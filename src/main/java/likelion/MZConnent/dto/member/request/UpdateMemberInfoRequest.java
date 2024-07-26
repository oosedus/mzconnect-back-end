package likelion.MZConnent.dto.member.request;

import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMemberInfoRequest {
    private String username;
    private List<Long> selfIntroductions;
    private String instagramId;
    private String facebookId;

}
