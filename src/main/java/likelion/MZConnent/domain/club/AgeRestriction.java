package likelion.MZConnent.domain.club;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum AgeRestriction {
    FOURTH_GRADE("4학년"),
    FIFTH_GRADE("5학년"),
    SIXTH_GRADE("6학년"),
    ALL("없음");

    private final String ageRestriction;

    @JsonValue
    public String getAgeRestriction() {
        return ageRestriction;
    }

    @JsonCreator
    public static AgeRestriction fromAgeRestriction(String ageRestriction) {
        for (AgeRestriction aR : AgeRestriction.values()) {
            if (aR.getAgeRestriction().equals(ageRestriction)) {
                return aR;
            }
        }
        throw new IllegalArgumentException(ageRestriction + "은 존재하지 않는 enum type입니다.(AgeRestriction)");
    }
}
