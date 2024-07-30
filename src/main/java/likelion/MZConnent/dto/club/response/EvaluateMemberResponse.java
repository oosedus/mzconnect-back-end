package likelion.MZConnent.dto.club.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluateMemberResponse {
    private int rateCount;

    public EvaluateMemberResponse(int rateCount) {
        this.rateCount = rateCount;
    }
}
