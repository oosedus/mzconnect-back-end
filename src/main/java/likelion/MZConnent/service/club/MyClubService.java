package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.club.*;
import likelion.MZConnent.domain.manner.Manner;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.SelfIntroductionDto;
import likelion.MZConnent.dto.club.request.UpdateClubInfoRequest;
import likelion.MZConnent.dto.club.response.MyClubDetailResponse;
import likelion.MZConnent.dto.club.response.MyClubSimpleResponse;
import likelion.MZConnent.dto.club.response.UpdateClubInfoResponse;
import likelion.MZConnent.repository.club.ClubMemberRepository;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.manner.MannerRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MyClubService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final MannerRepository mannerRepository;

    public MyClubSimpleResponse getMyClubs(String email) {
        Member member = getMemberByEmail(email);
        List<Club> clubList = getClubsByMember(member);

        List<MyClubSimpleResponse.MyClubSimpleDto> myClubs = clubList.stream()
                .map(this::convertToSimpleDto)
                .collect(Collectors.toList());

        return MyClubSimpleResponse.builder()
                .myClubs(myClubs)
                .build();
    }

    public MyClubDetailResponse getMyClubDetail(String email, Long clubId) {
        Member member = getMemberByEmail(email);
        Club club = getClubByMemberAndId(member, clubId);

        MyClubDetailResponse.MyClubCultureDto cultureDto = convertToCultureDto(club);
        List<MyClubDetailResponse.MyClubMemberDto> memberDtos = convertToMemberDtos(club);

        return MyClubDetailResponse.builder()
                .memberId(member.getId())
                .clubId(club.getClubId())
                .title(club.getTitle())
                .meetingDate(club.getMeetingDate())
                .content(club.getContent())
                .currentParticipant(club.getClubMembers().size())
                .culture(cultureDto)
                .members(memberDtos)
                .build();
    }

    public void closeClub(String email, Long clubId) {
        Member member = getMemberByEmail(email);
        ClubMember clubMember = getClubMemberByMemberAndId(member, clubId);
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));

        // 클럽장이 아닌 경우 예외 처리
        if (!clubMember.getClubRole().equals(ClubRole.LEADER)){
            throw new IllegalArgumentException("클럽장만 클럽을 종료할 수 있습니다.");
        }

        club.setStatus("CLOSE");
        clubRepository.save(club);
    }

    public void deleteClubMember(String email, Long clubId, Long targetMemberId) {
        Member member = getMemberByEmail(email);
        Club club = getClubById(clubId);
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버를 찾을 수 없습니다."));

        ClubMember clubMember = getClubMemberByMemberAndId(member, clubId);
        ClubMember targetClubMember = getClubMemberByMemberAndId(targetMember, clubId);

        // 클럽장이 아닌 경우 예외 처리
        if (!clubMember.getClubRole().equals(ClubRole.LEADER)){
            throw new IllegalArgumentException("클럽장만 멤버를 삭제할 수 있습니다.");
        }

        // 해당 클럽 멤버와 관련된 manner 엔티티의 clubMember 필드를 null로 설정
        List<Manner> manners = mannerRepository.findByClubMember(targetClubMember);
        manners.forEach(manner -> manner.setClubMember(null));
        mannerRepository.saveAll(manners);

        clubMemberRepository.delete(targetClubMember);
        club.setCurrentParticipant(club.getCurrentParticipant() - 1);
        clubRepository.save(club);
    }

    public void leaveClub(String email, Long clubId) {
        Member member = getMemberByEmail(email);
        Club club = getClubById(clubId);
        ClubMember clubMember = getClubMemberByMemberAndId(member, clubId);

        // 클럽장인 경우 예외 처리
        if (clubMember.getClubRole().equals(ClubRole.LEADER)){
            throw new IllegalArgumentException("클럽장은 클럽을 탈퇴할 수 없습니다.");
        }

        // 해당 클럽 멤버와 관련된 manner 엔티티의 clubMember 필드를 null로 설정
        List<Manner> manners = mannerRepository.findByClubMember(clubMember);
        manners.forEach(manner -> manner.setClubMember(null));
        mannerRepository.saveAll(manners);

        clubMemberRepository.delete(clubMember);
        club.setCurrentParticipant(club.getCurrentParticipant() - 1);
        clubRepository.save(club);
    }

    public UpdateClubInfoResponse updateClubInfo(String email, Long clubId, UpdateClubInfoRequest request) {
        Member member = getMemberByEmail(email);
        Club club = getClubById(clubId);
        getClubMemberByMemberAndId(member, clubId);

        validateRequest(request, club);

        updateClubFields(club, request);

        clubRepository.save(club);

        return buildUpdateClubInfoResponse(club);
    }

    // request 값들 중에 예외처리가 필요한 것들이 있는지 확인
    private void validateRequest(UpdateClubInfoRequest request, Club club) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new IllegalArgumentException("제목 값이 비어있습니다.");
        }
        if (request.getMeetingDate() == null || request.getMeetingDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("모임 날짜 값이 현재 날짜보다 과거로 설정되어 있습니다.");
        }
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new IllegalArgumentException("내용 값이 비어있습니다.");
        }
        if (request.getAgeRestriction() == null) {
            throw new IllegalArgumentException("나이 제한 값이 비어있습니다.");
        }
        if ((request.getAgeRestriction() != AgeRestriction.ALL) && isAgeRestrictionConflict(request.getAgeRestriction(), club)) {
            throw new IllegalArgumentException("현재 멤버들 중에 수정한 나이 제한인 값이 존재합니다.");
        }
        if (request.getGenderRestriction() == null) {
            throw new IllegalArgumentException("성별 제한 값이 비어있습니다.");
        }
        if ((request.getGenderRestriction() != GenderRestriction.ALL) && isGenderRestrictionConflict(request.getGenderRestriction(), club)) {
            throw new IllegalArgumentException("현재 멤버들 중에 수정한 성별 제한인 값이 존재합니다.");
        }
        if (request.getMaxParticipant() != null && request.getMaxParticipant() < 2) {
            throw new IllegalArgumentException("최대 인원 값이 2 미만으로 설정되어 있습니다.");
        }
        if (request.getMaxParticipant() != null && request.getMaxParticipant() < club.getClubMembers().size()) {
            throw new IllegalArgumentException("현재 인원보다 적게 설정되어 있습니다.");
        }
    }

    private boolean isGenderRestrictionConflict(GenderRestriction genderRestriction, Club club) {
        return club.getClubMembers().stream()
                .anyMatch(cm -> !cm.getMember().getGender().toString().equals(genderRestriction.toString()));
    }

    private boolean isAgeRestrictionConflict(AgeRestriction ageRestriction, Club club) {
        return club.getClubMembers().stream()
                .anyMatch(cm -> !cm.getMember().getAge().toString().equals(ageRestriction.toString()));
    }

    // 클럽 필드 업데이트
    private void updateClubFields(Club club, UpdateClubInfoRequest request) {
        club.setTitle(request.getTitle());
        club.setMeetingDate(request.getMeetingDate());
        club.setContent(request.getContent());
        club.setGenderRestriction(request.getGenderRestriction());
        if (request.getMaxParticipant() != null) {
            club.setMaxParticipant(request.getMaxParticipant());
        }
    }

    // UpdateClubInfoResponse 빌드
    private UpdateClubInfoResponse buildUpdateClubInfoResponse(Club club) {
        return UpdateClubInfoResponse.builder()
                .clubId(club.getClubId())
                .title(club.getTitle())
                .meetingDate(club.getMeetingDate())
                .content(club.getContent())
                .ageRestriction(club.getAgeRestriction())
                .genderRestriction(club.getGenderRestriction())
                .maxParticipant(club.getMaxParticipant())
                .cultureName(club.getCulture().getName())
                .build();
    }

    private List<Club> getClubsByMember(Member member) {
        return member.getClubMembers().stream()
                .map(ClubMember::getClub)
                .collect(Collectors.toList());
    }

    private MyClubSimpleResponse.MyClubSimpleDto convertToSimpleDto(Club club) {
        return MyClubSimpleResponse.MyClubSimpleDto.builder()
                .clubId(club.getClubId())
                .title(club.getTitle())
                .cultureName(club.getCulture().getName())
                .meetingDate(club.getMeetingDate())
                .currentParticipant(club.getClubMembers().size())
                .maxParticipant(club.getMaxParticipant())
                .build();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Club getClubByMemberAndId(Member member, Long clubId) {
        return member.getClubMembers().stream()
                .map(ClubMember::getClub)
                .filter(c -> c.getClubId().equals(clubId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 클럽을 찾을 수 없습니다."));
    }

    private MyClubDetailResponse.MyClubCultureDto convertToCultureDto(Club club) {
        return MyClubDetailResponse.MyClubCultureDto.builder()
                .cultureId(club.getCulture().getCultureId())
                .name(club.getCulture().getName())
                .build();
    }

    private List<MyClubDetailResponse.MyClubMemberDto> convertToMemberDtos(Club club) {
        return club.getClubMembers().stream()
                .map(cm -> MyClubDetailResponse.MyClubMemberDto.builder()
                        .userId(cm.getMember().getId())
                        .username(cm.getMember().getUsername())
                        .profileImageUrl(cm.getMember().getProfileImageUrl())
                        .age(cm.getMember().getAge())
                        .gender(cm.getMember().getGender())
                        .role(cm.getClubRole())
                        .selfIntroductions(cm.getMember().getSelfIntroductions().stream()
                                .map(si -> SelfIntroductionDto.builder()
                                        .cultureCategoryId(si.getCultureCategory().getId())
                                        .name(si.getCultureCategory().getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    private ClubMember getClubMemberByMemberAndId(Member member, Long clubId) {
        return member.getClubMembers().stream()
                .filter(cm -> cm.getClub().getClubId().equals(clubId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 가입된 모임이 아닙니다."));
    }

    private Club getClubById(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));
    }
}
