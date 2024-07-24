package likelion.MZConnent.repository.member;

import likelion.MZConnent.domain.member.SelfIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfIntroductionRepository extends JpaRepository<SelfIntroduction, Long> {
}

