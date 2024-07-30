package likelion.MZConnent.repository.manner;

import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.manner.Manner;
import likelion.MZConnent.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MannerRepository extends JpaRepository<Manner, Long> {
    long countByMemberAndClubMember(Member member, ClubMember clubMember);
    List<Manner> findByMember(Member member);
    List<Manner> findByClubMember(ClubMember targetClubMember);
}
