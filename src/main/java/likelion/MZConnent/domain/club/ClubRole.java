package likelion.MZConnent.domain.club;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public enum ClubRole {
    LEADER("모임장"),
    MEMBER("모임원");

    private final String clubRole;

    public String getClubRole() {
        return clubRole;
    }

    public static ClubRole fromRole(String role) {
        for (ClubRole cR : ClubRole.values()) {
            if (cR.getClubRole().equals(role)) {
                return cR;
            }
        }
        log.info("존재하지 않는 enum type(ClubRole): {}", role);
        throw new IllegalArgumentException(role + "은 존재하지 않는 enum type입니다.(ClubRole)");
    }
}
