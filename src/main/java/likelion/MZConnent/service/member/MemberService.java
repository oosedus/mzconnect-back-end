package likelion.MZConnent.service.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.MZConnent.domain.culture.CultureCategory;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.member.SelfIntroduction;
import likelion.MZConnent.dto.member.request.UpdateMemberInfoRequest;
import likelion.MZConnent.dto.member.response.MemberInfoResponse;
import likelion.MZConnent.dto.member.response.UpdateMemberInfoResponse;
import likelion.MZConnent.repository.culture.CultureCategoryRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import likelion.MZConnent.repository.member.SelfIntroductionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final SelfIntroductionRepository selfIntroductionRepository;
    private final CultureCategoryRepository cultureCategoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MemberInfoResponse getMemberInfoByEmail(String email) {
        return new MemberInfoResponse(findMemberByEmail(email));
    }


    public MemberInfoResponse getMemberInfoById(Long id) {
        return new MemberInfoResponse(findMemberById(id));
    }


    public UpdateMemberInfoResponse updateMemberInfo(String email, UpdateMemberInfoRequest request) {
        log.info(request.getFacebookId());
        log.info(request.getInstagramId());

        Member member = findMemberByEmail(email);
        member.setUsername(request.getUsername());
        member.setInstagramId(request.getInstagramId());
        member.setFacebookId(request.getFacebookId());

        member.getSelfIntroductions().clear();
        selfIntroductionRepository.deleteByMemberEmail(email);

        List<SelfIntroduction> selfIntroductions = request.getSelfIntroductions().stream()
                .map((id) -> {
                    SelfIntroduction selfIntroduction = SelfIntroduction.builder()
                            .member(member)
                            .cultureCategory(findCultureCategoryById(id))
                            .build();
                    selfIntroductionRepository.save(selfIntroduction);
                    return selfIntroduction;
                }).collect(Collectors.toList());

        member.setSelfIntroductions(selfIntroductions);

        return UpdateMemberInfoResponse.builder()
                .member(member)
                .build();
    }


    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> {
            log.info("계정이 존재하지 않음.");
            return new IllegalArgumentException("계정이 존재하지 않습니다.");
        });
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> {
            log.info("계정이 존재하지 않음.");
            return new IllegalArgumentException("계정이 존재하지 않습니다.");
        });
    }

    private CultureCategory findCultureCategoryById(Long id) {
        return cultureCategoryRepository.findById(id).orElseThrow(
                ()->{
                    log.info("문화 카테고리가 존재하지 않음.");
                    return new IllegalArgumentException("문화 카테고리가 존재하지 않습니다.");
                }
        );
    }
}
