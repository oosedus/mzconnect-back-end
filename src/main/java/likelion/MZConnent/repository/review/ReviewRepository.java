package likelion.MZConnent.repository.review;

import io.lettuce.core.dynamic.annotation.Param;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.title LIKE %:keyword%")
    Page<Review> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT r FROM Review r ORDER BY r.likeCount DESC")
    List<Review> findTop6ByLikeCount(Pageable pageable);
}
