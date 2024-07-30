package likelion.MZConnent.dto.club.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluateMemberRequest {
    private int score;

    public EvaluateMemberRequest(int score) {
        this.score = score;
    }
}
