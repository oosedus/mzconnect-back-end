package likelion.MZConnent.repository.culture;

import likelion.MZConnent.domain.culture.CultureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CultureCategoryRepository extends JpaRepository<CultureCategory, Long> {
}
