package likelion.MZConnent.repository.review;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.culture.CultureInterest;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.domain.review.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    boolean existsByMemberAndReview(Member member, Review review);

    Optional<ReviewLike> findByMemberAndReview(Member member, Review review);

    List<ReviewLike> findAllByReview(Review review);
}
