package likelion.MZConnent.repository.club;

import likelion.MZConnent.domain.club.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
}
