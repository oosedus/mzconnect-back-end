package likelion.MZConnent.domain.culture;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
