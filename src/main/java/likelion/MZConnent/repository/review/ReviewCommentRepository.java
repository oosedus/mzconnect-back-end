package likelion.MZConnent.repository.review;

import likelion.MZConnent.domain.review.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
}

