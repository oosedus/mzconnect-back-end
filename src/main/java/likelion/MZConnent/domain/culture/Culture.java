package likelion.MZConnent.domain.culture;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.RegionCategory;
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
    private int interestCount = 0;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String cultureImageUrl;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(nullable = false)
    private int clubCount = 0;

    @Column(length = 100, nullable = false)
    private String recommendedMember;

    @ManyToOne
    @JoinColumn(name = "cultureCategoryId", nullable = false)
    private CultureCategory cultureCategory;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private RegionCategory region;

    @OneToMany(mappedBy = "culture")
    private List<Club> clubs;

    @OneToMany(mappedBy = "culture")
    private List<CultureInterest> cultureInterests;

    @OneToMany(mappedBy = "culture")
    private List<Review> reviews;


    @Builder
    public Culture(int interestCount, String content, String cultureImageUrl, String name, String summary, int clubCount, String recommendedMember, CultureCategory cultureCategory, RegionCategory region) {
        this.interestCount = interestCount;
        this.content = content;
        this.cultureImageUrl = cultureImageUrl;
        this.name = name;
        this.summary = summary;
        this.clubCount = clubCount;
        this.recommendedMember = recommendedMember;
        this.cultureCategory = cultureCategory;
        this.region = region;
    }
}
