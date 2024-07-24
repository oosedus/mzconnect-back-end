package likelion.MZConnent.repository.review;

import likelion.MZConnent.domain.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
}
