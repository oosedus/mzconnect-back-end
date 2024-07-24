package likelion.MZConnent.repository.club;

import likelion.MZConnent.domain.club.RegionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionCategoryRepository extends JpaRepository<RegionCategory, Long> {
}

