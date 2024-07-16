package likelion.MZConnent.domain.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String password;

    @Column
    private String realname;

    @Column(length = 50)
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    @Builder
    public Member(Long id, String email, String password, String realname, String username, Role role, Gender gender, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.realname = realname;
        this.username = username;
        this.role = role;
        this.gender = gender;
        this.age = age;
    }
}
