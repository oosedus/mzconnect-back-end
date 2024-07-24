package likelion.MZConnent.domain.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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

    @Enumerated(EnumType.STRING)
    private Age age;

    private String instagramId;

    private String facebookId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<SelfIntroduction> selfIntroductions = new ArrayList<>();


    @Builder
    public Member(String email, String password, String realname, String username, Role role, Gender gender, Age age, String instagramId, String facebookId, List<SelfIntroduction> selfIntroductions) {
        this.email = email;
        this.password = password;
        this.realname = realname;
        this.username = username;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.instagramId = instagramId;
        this.facebookId = facebookId;
        this.selfIntroductions = selfIntroductions;
    }
}
