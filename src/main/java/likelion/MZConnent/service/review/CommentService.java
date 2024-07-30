package likelion.MZConnent.service.review;

import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.domain.review.ReviewComment;
import likelion.MZConnent.dto.culture.request.CreateCultureRequest;
import likelion.MZConnent.dto.review.request.SaveCommentRequest;
import likelion.MZConnent.dto.review.response.SaveCommentResponse;
import likelion.MZConnent.repository.member.MemberRepository;
import likelion.MZConnent.repository.review.ReviewCommentRepository;
import likelion.MZConnent.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public SaveCommentResponse saveComment(String email, Long reviewId, String content) {
        Member member = findMemberByEmail(email);
        Review review = findReviewById(reviewId);

        ReviewComment comment = ReviewComment.builder()
                .content(content)
                .createdDate(LocalDateTime.now())
                .member(member)
                .review(review)
                .build();

        reviewCommentRepository.save(comment);

        return SaveCommentResponse.builder().comment(comment).build();
    }

    private Review findReviewById(Long reviewId ) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.info("후기가 존재하지 않음.");
            return new IllegalArgumentException("후기가 존재하지 않습니다.");
        });
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            log.info("회원이 존재하지 않음.");
            return new IllegalArgumentException("회원이 존재하지 않습니다.");
        });
    }
}
