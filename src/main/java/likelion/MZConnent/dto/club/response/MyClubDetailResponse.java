package likelion.MZConnent.dto.club.response;

import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import likelion.MZConnent.dto.club.SelfIntroductionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyClubDetailResponse {
    private Long memberId;
    private Long clubId;
    private String title;
    private LocalDate meetingDate;
    private String content;
    private int currentParticipant;
    private MyClubCultureDto culture;
    private List<MyClubMemberDto> members;

    @Builder
    public MyClubDetailResponse(Long memberId, Long clubId, String title, LocalDate meetingDate, String content, int currentParticipant, MyClubCultureDto culture, List<MyClubMemberDto> members) {
        this.memberId = memberId;
        this.clubId = clubId;
        this.title = title;
        this.meetingDate = meetingDate;
        this.content = content;
        this.currentParticipant = currentParticipant;
        this.culture = culture;
        this.members = members;
    }

    @Getter
    @NoArgsConstructor
    public static class MyClubCultureDto {
        private Long cultureId;
        private String name;

        @Builder
        public MyClubCultureDto(Long cultureId, String name) {
            this.cultureId = cultureId;
            this.name = name;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class MyClubMemberDto {
        private Long userId;
        private String username;
        private String profileImageUrl;
        private Age age;
        private Gender gender;
        private ClubRole role;
        private List<SelfIntroductionDto> selfIntroductions;

        @Builder
        public MyClubMemberDto(Long userId, String username, String profileImageUrl, Age age, Gender gender, ClubRole role, List<SelfIntroductionDto> selfIntroductions) {
            this.userId = userId;
            this.username = username;
            this.profileImageUrl = profileImageUrl;
            this.age = age;
            this.gender = gender;
            this.role = role;
            this.selfIntroductions = selfIntroductions;
        }
    }
}
