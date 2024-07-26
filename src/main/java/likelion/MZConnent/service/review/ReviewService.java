package likelion.MZConnent.service.review;

import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
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
}
