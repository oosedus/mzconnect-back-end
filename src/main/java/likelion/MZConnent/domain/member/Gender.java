package likelion.MZConnent.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private final String name;

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Gender fromName(String name) {
        for (Gender gender : Gender.values()) {
            if (gender.getName().equals(name)) {
                return gender;
            }
        }
        log.info("존재하지 않는 enum type(Gender): {}", name);
        throw new IllegalArgumentException(name +"은 존재하지 않는 enum type입니다.(Gender)");
    }

}
