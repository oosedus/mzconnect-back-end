package likelion.MZConnent.domain.culture;

import jakarta.persistence.*;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Culture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cultureId;

    @Column(nullable = false)
    private int interestCount;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String cultureImageUrl;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(nullable = false)
    private int cultureCount;

    @Column(length = 100, nullable = false)
    private String recommendedMember;

    @ManyToOne
    @JoinColumn(name = "cultureCategoryId", nullable = false)
    private CultureCategory cultureCategory;

    @OneToMany(mappedBy = "culture")
    private List<Club> clubs;

    @OneToMany(mappedBy = "culture")
    private List<CultureInterest> cultureInterests;

    @OneToMany(mappedBy = "culture")
    private List<Review> reviews;

    @Builder
    public Culture(int interestCount, String content, String cultureImageUrl, String name, String summary, int cultureCount, String recommendedMember, CultureCategory cultureCategory) {
        this.interestCount = interestCount;
        this.content = content;
        this.cultureImageUrl = cultureImageUrl;
        this.name = name;
        this.summary = summary;
        this.cultureCount = cultureCount;
        this.recommendedMember = recommendedMember;
        this.cultureCategory = cultureCategory;
    }
}
