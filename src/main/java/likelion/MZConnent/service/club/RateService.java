package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.manner.Manner;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.request.EvaluateMemberRequest;
import likelion.MZConnent.dto.club.response.EvaluateMemberResponse;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.manner.MannerRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RateService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final MannerRepository mannerRepository;

    public EvaluateMemberResponse evaluateMember(String email, Long clubId, Long evaluateeId, EvaluateMemberRequest request) {
        Member member = getMemberByEmail(email);
        Member evaluatee = getMemberById(evaluateeId);
        Club club = getClubById(clubId);

        // 해당 유저들이 해당 모임에 가입되어 있는지 확인
        validateClubMember(club, member, evaluatee);
        ClubMember memberClubMember = getClubMember(club, member);

        // 평가자가 해당 멤버에게 평가한 횟수 조회
        // 평가자가 해당 멤버의 평가를 2번 이상한 경우 예외 처리
        long evaluationCount = mannerRepository.countByMemberAndClubMember(evaluatee, memberClubMember);
        if (evaluationCount >= 2) {
            throw new IllegalArgumentException("해당 멤버에게 2번 이상 평가할 수 없습니다.");
        }

        // 평가 점수 저장
        saveEvaluation(request.getScore(), evaluatee, memberClubMember);

        // 평가 점수 평균 계산 및 업데이트
        updateAverageMannersScore(evaluatee);

        // 평가자가 해당 멤버에게 평가한 횟수 반환
        return new EvaluateMemberResponse((int) (evaluationCount + 1));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Member getMemberById(Long evaluateeId) {
        return memberRepository.findById(evaluateeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Club getClubById(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));
    }

    private void validateClubMember(Club club, Member evaluator, Member evaluatee) {
        boolean isMemberJoined = club.getClubMembers().stream()
                .anyMatch(cm -> cm.getMember().getId().equals(evaluator.getId()));
        boolean isEvaluateeJoined = club.getClubMembers().stream()
                .anyMatch(cm -> cm.getMember().getId().equals(evaluatee.getId()));
        if (!isMemberJoined || !isEvaluateeJoined) {
            throw new IllegalArgumentException("해당 사용자는 해당 모임에 가입되어 있지 않습니다.");
        }
    }

    private ClubMember getClubMember(Club club, Member member) {
        return club.getClubMembers().stream()
                .filter(cm -> cm.getMember().equals(member))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 클럽 멤버에서 찾을 수 없습니다."));
    }

    private void saveEvaluation(int score, Member evaluatee, ClubMember memberClubMember) {
        Manner manner = Manner.builder()
                .score(score)
                .member(evaluatee)
                .clubMember(memberClubMember)
                .build();
        mannerRepository.save(manner);
    }

    private void updateAverageMannersScore(Member evaluatee) {
        List<Manner> allMannersForEvaluatee = mannerRepository.findByMember(evaluatee);
        double averageScore = allMannersForEvaluatee.stream()
                .mapToInt(Manner::getScore)
                .average()
                .getAsDouble();
        evaluatee.setAverageMannersScore(BigDecimal.valueOf(averageScore));
        memberRepository.save(evaluatee);
    }

}
