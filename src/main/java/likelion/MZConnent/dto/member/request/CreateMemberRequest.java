package likelion.MZConnent.dto.member.request;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateMemberRequest {
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotBlank
    @Size(min = 2, max = 5, message = "이름은 2자 이상, 5자 이하여야합니다.")
    private String realname;

    @NotBlank
    @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하여야합니다.")
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Age age;

    private String instagramId;

    private String facebookId;

    private List<Long> selfIntroductions = new ArrayList<>();

    @Builder
    public CreateMemberRequest(String email, String password, String realname, String username, Gender gender, Age age, String instagramId, String facebookId, List<Long> selfIntroductions) {
        this.email = email;
        this.password = password;
        this.realname = realname;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.instagramId = instagramId;
        this.facebookId = facebookId;
        this.selfIntroductions = selfIntroductions;
    }
}
