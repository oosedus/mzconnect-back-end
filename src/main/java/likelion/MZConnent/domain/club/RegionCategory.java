package likelion.MZConnent.domain.club;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "region_categories")
public class RegionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "region")
    private List<Club> clubs;

}

