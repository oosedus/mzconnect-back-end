package likelion.MZConnent.repository.culture;

import io.lettuce.core.dynamic.annotation.Param;
import likelion.MZConnent.domain.culture.Culture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CultureRepository extends JpaRepository<Culture, Long> {
    @Query("SELECT c FROM Culture c WHERE :category = 0 OR c.cultureCategory.id = :category")
    Page<Culture> findAllByCultureCategory(@Param("category") Long category, Pageable pageable);

    @Query("SELECT c FROM Culture c JOIN c.cultureInterests ci JOIN ci.member m WHERE (:category = 0 OR c.cultureCategory.id = :category) AND m.email = :email")
    Page<Culture> findByMemberAndCategory(@Param("email") String email, @Param("category") Long category, Pageable pageable);
}
