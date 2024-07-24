package likelion.MZConnent.repository.self;

import likelion.MZConnent.domain.self.SelfIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfIntroductionRepository extends JpaRepository<SelfIntroduction, Long> {
}

