package likelion.MZConnent.service.culture;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.culture.CultureInterest;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.review.Review;
import likelion.MZConnent.dto.culture.response.CultureDetailResponse;
import likelion.MZConnent.dto.culture.response.CulturesSimpleResponse;
import likelion.MZConnent.dto.paging.response.PageContentResponse;
import likelion.MZConnent.dto.review.response.ReviewsSimpleResponse;
import likelion.MZConnent.repository.culture.CultureInterestRepository;
import likelion.MZConnent.repository.culture.CultureRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import likelion.MZConnent.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CultureService {
    private final CultureRepository cultureRepository;
    private final CultureInterestRepository cultureInterestRepository;
    private final MemberRepository memberRepository;

    private final int PAGE_SIZE = 6;

    public PageContentResponse<CulturesSimpleResponse> getCulturesSimpleList(Long cultureCategoryId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Culture> cultures = cultureRepository.findAllByCultureCategory(cultureCategoryId, pageable);

        List<CulturesSimpleResponse> cultureResponse = cultures.stream().map(culture -> CulturesSimpleResponse.builder()
                .culture(culture).build()
        ).collect(Collectors.toList());

        return PageContentResponse.<CulturesSimpleResponse>builder()
                .content(cultureResponse)
                .totalPages(cultures.getTotalPages())
                .totalElements(cultures.getTotalElements())
                .size(pageable.getPageSize())
                .build();

    }

    public PageContentResponse<CulturesSimpleResponse> getMyIntersetCulturesSimpleList(String email, Long cultureCategoryId, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Page<Culture> cultures = cultureRepository.findByMemberAndCategory(email, cultureCategoryId, pageable);

        List<CulturesSimpleResponse> cultureResponse = cultures.stream().map(culture -> CulturesSimpleResponse.builder()
                .culture(culture).build()
        ).collect(Collectors.toList());

        return PageContentResponse.<CulturesSimpleResponse>builder()
                .content(cultureResponse)
                .totalPages(cultures.getTotalPages())
                .totalElements(cultures.getTotalElements())
                .size(pageable.getPageSize())
                .build();

    }

    public CultureDetailResponse getCultureDetailInfo(Long cultureId) {
        Culture culture = getCultureById(cultureId);
        return CultureDetailResponse.builder()
                .culture(culture).build();
    }


    // 문화 관심 토글
    public boolean toggleCultureInterest(String email, Long cultureId) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            log.info("해당 회원이 존재하지 않음.");
            return new IllegalArgumentException("해당 회원이 존재하지 않습니다.");
        });
        Culture culture = getCultureById(cultureId);

        boolean exists = cultureInterestRepository.existsByMemberAndCulture(member, culture);


        if (exists) { // 이미 문화를 관심 등록 해놓은 경우 (삭제)
            CultureInterest cultureInterest = cultureInterestRepository.findByMemberAndCulture(member, culture)
                    .get();
            cultureInterestRepository.delete(cultureInterest);

            member.getCultureInterests().remove(cultureInterest);
            culture.getCultureInterests().remove(cultureInterest);
            culture.setInterestCount(culture.getInterestCount() - 1);
            return false;
        }
        else { // 문화를 관심 등록 해놓지 않은 경우 (추가)
            CultureInterest cultureInterest = cultureInterestRepository.save(CultureInterest.builder()
                    .member(member)
                    .culture(culture)
                    .build());

            member.getCultureInterests().add(cultureInterest);
            culture.getCultureInterests().add(cultureInterest);
            culture.setInterestCount(culture.getInterestCount()+1);

            return true;
        }


    }

    private Culture getCultureById(Long cultureId) {
        Culture culture = cultureRepository.findById(cultureId).orElseThrow(() -> {
            log.info("해당 문화가 존재하지 않음.");
            return new IllegalArgumentException("해당 문화가 존재하지 않습니다.");
        });
        return culture;
    }
}
