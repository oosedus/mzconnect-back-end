package likelion.MZConnent.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import likelion.MZConnent.domain.chat.Chat;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.culture.CultureInterest;
import likelion.MZConnent.domain.manner.Manner;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.domain.review.ReviewComment;
import likelion.MZConnent.domain.review.ReviewLike;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    public static final String DEFAULT_PROFILE_IMAGE_URL = "https://mzconnect-bucket.s3.ap-northeast-2.amazonaws.com/default_image.jpeg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 50)
    private String email;

    @Column(length = 100)
    private String password;

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

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl = DEFAULT_PROFILE_IMAGE_URL;

    @Column(precision = 2, scale = 1)
    private BigDecimal averageMannersScore = BigDecimal.valueOf(4);

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<SelfIntroduction> selfIntroductions = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReviewComment> reviewComments;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Chat> chats;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<CultureInterest> cultureInterests;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ReviewLike> reviewLikes;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Manner> manners;

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
