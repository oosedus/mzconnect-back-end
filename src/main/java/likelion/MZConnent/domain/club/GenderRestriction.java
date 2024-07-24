package likelion.MZConnent.domain.club;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public enum GenderRestriction {
    MALE("남자"),
    FEMALE("여자"),
    ALL("없음");

    private final String genderRestriction;

    @JsonValue
    public String getGenderRestriction() {
        return genderRestriction;
    }

    @JsonCreator
    public static GenderRestriction fromGenderRestriction(String genderRestriction) {
        for (GenderRestriction gR : GenderRestriction.values()) {
            if (gR.getGenderRestriction().equals(genderRestriction)) {
                return gR;
            }
        }
        throw new IllegalArgumentException(genderRestriction + "은 존재하지 않는 enum type입니다.(GenderRestriction)");
    }
}
