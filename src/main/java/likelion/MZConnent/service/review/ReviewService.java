package likelion.MZConnent.service.review;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.request.SaveReviewRequest;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.dto.review.response.SaveReviewResponse;
import likelion.MZConnent.repository.culture.CultureRepository;
import likelion.MZConnent.repository.member.MemberRepository;
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

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        Culture culture = cultureRepository.findById(cultureId).orElseThrow(() -> new IllegalArgumentException("문화 정보를 찾을 수 없습니다."));

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
}
