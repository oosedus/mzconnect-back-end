package likelion.MZConnent.service.main;

import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.main.response.MainInfoResponse;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.culture.CultureRepository;
import likelion.MZConnent.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainInfoService {
    private final ReviewRepository reviewRepository;
    private final CultureRepository cultureRepository;
    private final ClubRepository clubRepository;
    public MainInfoResponse
    getMainInfo() {
        Pageable top4 = PageRequest.of(0, 4);
        Pageable top6 = PageRequest.of(0, 3);

        List<Culture> popularCultures = cultureRepository.findTop4ByInterestCount(top4);
        List<Review> popularReviews = reviewRepository.findTop6ByLikeCount(top6);
        List<Club> recentClubs = clubRepository.findTop6OrderByCreatedDateDesc(top6);


        return MainInfoResponse.builder()
                .popularCultures(popularCultures)
                .popularReviews(popularReviews)
                .recentClubs(recentClubs)
                .build();
    }
}
