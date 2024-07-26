package likelion.MZConnent.repository.member;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.member.SelfIntroduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfIntroductionRepository extends JpaRepository<SelfIntroduction, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM SelfIntroduction si WHERE si.member.email = :email")
    void deleteByMemberEmail(@Param("email") String email);
}
