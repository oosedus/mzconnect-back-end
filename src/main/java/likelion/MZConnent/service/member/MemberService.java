package likelion.MZConnent.service.member;

import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.member.response.MemberInfoResponse;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberInfoResponse getMemberInfoByEmail(String email) {
        return new MemberInfoResponse(findMemberByEmail(email));
    }

    public MemberInfoResponse getMemberInfoById(Long id) {
        return new MemberInfoResponse(findMemberById(id));
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
}
