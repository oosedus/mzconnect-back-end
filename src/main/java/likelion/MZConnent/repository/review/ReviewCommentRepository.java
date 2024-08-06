package likelion.MZConnent.repository.review;

import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.domain.review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
    List<ReviewComment> findAllByReview(Review review);
}

