package likelion.MZConnent.repository.club;

import likelion.MZConnent.domain.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    // status가 OPEN인 클럽만 조회
    // cultureId와 regionId가 0이면 무시
    // cultureId와 regionId가 0이 아니면 해당 cultureId와 regionId에 맞는 클럽만 조회
    @Query("SELECT c FROM Club c WHERE c.status = 'OPEN' " +
            "AND (:cultureId = 0 OR c.culture.cultureId = :cultureId) " +
            "AND (:regionId = 0 OR c.region.regionId = :regionId)")
    Page<Club> findAllByFilters(@Param("cultureId") Long cultureId,
                                @Param("regionId") Long regionId,
                                Pageable pageable);

    Collection<Club> findAllByStatus(String status);

    @Query("SELECT c FROM Club c WHERE c.status = 'OPEN' ORDER BY c.createdDate DESC")
    List<Club> findTop6OrderByCreatedDateDesc(Pageable pageable);
}

