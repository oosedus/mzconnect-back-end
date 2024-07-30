package likelion.MZConnent.repository.culture;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.culture.CultureInterest;
import likelion.MZConnent.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CultureInterestRepository extends JpaRepository<CultureInterest, Long> {

    boolean existsByMemberAndCulture(Member member, Culture culture);

    Optional<CultureInterest> findByMemberAndCulture(Member member, Culture culture);
}