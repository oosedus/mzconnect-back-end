package likelion.MZConnent.domain.club;

import jakarta.persistence.*;
import likelion.MZConnent.domain.culture.Culture;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class RegionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "region")
    private List<Club> clubs;

    @OneToMany(mappedBy = "region")
    private List<Culture> cultures;
}

