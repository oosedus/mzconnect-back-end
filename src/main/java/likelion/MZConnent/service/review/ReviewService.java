package likelion.MZConnent.service.review;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.culture.CultureInterest;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.domain.review.ReviewLike;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.request.SaveReviewRequest;
import likelion.MZConnent.dto.review.response.ReviewDetailResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.dto.review.response.SaveReviewResponse;
import likelion.MZConnent.repository.culture.CultureRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import likelion.MZConnent.repository.review.ReviewLikeRepository;
import likelion.MZConnent.repository.review.ReviewRepository;
import likelion.MZConnent.service.image.S3ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final S3ImageService s3ImageService;
    private final MemberRepository memberRepository;
    private final CultureRepository cultureRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final int PAGE_SIZE = 6;

    public PageContentResponse<ReviewsSimpleResponse> getReviewsSimpleList(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdDate").descending());

        log.info(keyword);

        Page<Review> reviews = reviewRepository.findByKeyword(keyword, pageable);

        List<ReviewsSimpleResponse> reviewsResponse = reviews.stream().map(review -> ReviewsSimpleResponse.builder()
                .review(review).build()
        ).collect(Collectors.toList());

        return PageContentResponse.<ReviewsSimpleResponse>builder()
                .content(reviewsResponse)
                .totalPages(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .size(pageable.getPageSize())
                .build();

    }

    public SaveReviewResponse createReview(String email, SaveReviewRequest request, List<MultipartFile> images, Long cultureId) throws IOException {
        List<String> imageUrls = uploadImages(images);

        Member member = findMemberByEmail(email);
        Culture culture = findCultureById(cultureId);

        Review review = buildReview(request, imageUrls, member, culture);
        review = reviewRepository.save(review);

        return buildSaveReviewResponse(review, member, culture);
    }

    private List<String> uploadImages(List<MultipartFile> images) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageUrl = s3ImageService.upload(image, "reviews");
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    private Review buildReview(SaveReviewRequest request, List<String> imageUrls, Member member, Culture culture) {
        return Review.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .reviewImageUrl1(imageUrls.size() > 0 ? imageUrls.get(0) : null)
                .reviewImageUrl2(imageUrls.size() > 1 ? imageUrls.get(1) : null)
                .reviewImageUrl3(imageUrls.size() > 2 ? imageUrls.get(2) : null)
                .reviewImageUrl4(imageUrls.size() > 3 ? imageUrls.get(3) : null)
                .createdDate(LocalDateTime.now())
                .likeCount(0)
                .commentCount(0)
                .culture(culture)
                .member(member)
                .build();
    }

    private SaveReviewResponse buildSaveReviewResponse(Review review, Member member, Culture culture) {
        return SaveReviewResponse.builder()
                .reviewId(review.getReviewId())
                .reviewer(SaveReviewResponse.ReviewerDto.builder()
                        .userId(member.getId())
                        .username(member.getUsername())
                        .profileImage(member.getProfileImageUrl())
                        .build())
                .culture(SaveReviewResponse.ReviewCultureDto.builder()
                        .cultureId(culture.getCultureId())
                        .cultureName(culture.getName())
                        .build())
                .title(review.getTitle())
                .reviewImageUrl1(review.getReviewImageUrl1())
                .reviewImageUrl2(review.getReviewImageUrl2())
                .reviewImageUrl3(review.getReviewImageUrl3())
                .reviewImageUrl4(review.getReviewImageUrl4())
                .createdDate(review.getCreatedDate())
                .likeCount(review.getLikeCount())
                .build();
    }

    public boolean toggleReviewLike(String email, Long reviewId) {

        Member member = findMemberByEmail(email);
        Review review = findReviewById(reviewId);

        boolean exists = reviewLikeRepository.existsByMemberAndReview(member, review);


        if (exists) { // 이미 후기 좋아요를 등록한 경우 (삭제)
            ReviewLike reviewLike = reviewLikeRepository.findByMemberAndReview(member, review).get();
            reviewLikeRepository.delete(reviewLike);

            member.getReviewLikes().remove(reviewLike);
            review.getReviewLikes().remove(reviewLike);
            review.setLikeCount(review.getLikeCount() - 1);
            return false;
        }
        else { // 후기 좋아요를 하지 않은 경우 (추가)
            ReviewLike reviewLike = reviewLikeRepository.save(ReviewLike.builder()
                    .member(member)
                    .review(review)
                    .build());

            member.getReviewLikes().add(reviewLike);
            review.getReviewLikes().add(reviewLike);
            review.setLikeCount(review.getLikeCount() + 1);
            return true;
        }
    }

    // 후기 상세 정보 조회
    public ReviewDetailResponse getReviewDetailInfo(Long reviewId) {
        return ReviewDetailResponse.builder().review(findReviewById(reviewId)).build();
    }

    private Review findReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> {
            log.info("후기 정보를 찾을 수 없음.");
            return new IllegalArgumentException("후기 정보를 찾을 수 없습니다.");
        });
    }
    private Culture findCultureById(Long cultureId) {
        return cultureRepository.findById(cultureId).orElseThrow(() -> {
            log.info("문화 정보를 찾을 수 없음.");
            return new IllegalArgumentException("문화 정보를 찾을 수 없습니다.");
        });
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            log.info("회원 정보를 찾을 수 없음.");
            return new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        });
    }
}
