package likelion.MZConnent.domain.culture;

import jakarta.persistence.*;
import likelion.MZConnent.domain.member.SelfIntroduction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "culture_categories")
public class CultureCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cultureCategoryId;

    @Column(length = 255, nullable = false)
    private String name;

    @OneToMany(mappedBy = "cultureCategory", cascade = CascadeType.ALL)
    private List<SelfIntroduction> selfIntroductions = new ArrayList<>();
}
