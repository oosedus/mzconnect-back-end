package likelion.MZConnent.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public enum Age {
    FOURTH_GRADE("4학년"),
    FIFTH_GRADE("5학년"),
    SIXTH_GRADE("6학년 이상");

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Age fromName(String name) {
        for (Age age : Age.values()) {
            if (age.getName().equals(name)) {
                return age;
            }
        }
        log.info("존재하지 않는 enum type(Age): {}", name);
        throw new IllegalArgumentException(name +"은 존재하지 않는 enum type입니다.(Age)");
    }
}
