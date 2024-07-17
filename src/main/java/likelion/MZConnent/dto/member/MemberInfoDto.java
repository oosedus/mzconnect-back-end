package likelion.MZConnent.dto.member;

import likelion.MZConnent.domain.member.Gender;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.member.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberInfoDto {
    private String email;
    private String realname;
    private String username;
    private Role role;
    private Gender gender;
    private Integer age;

    @Builder
    public MemberInfoDto(String email, String realname, String username, Role role, Gender gender, Integer age) {
        this.email = email;
        this.realname = realname;
        this.username = username;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }

    public static MemberInfoDto toDto(Member member) {
        return MemberInfoDto.builder()
                .email(member.getEmail())
                .realname(member.getRealname())
                .username(member.getUsername())
                .role(member.getRole())
                .gender(member.getGender())
                .age(member.getAge())
                .build();
    }
}
