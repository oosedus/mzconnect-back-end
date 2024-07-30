package likelion.MZConnent.service.club;

import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.SelfIntroductionDto;
import likelion.MZConnent.dto.club.response.MyClubDetailResponse;
import likelion.MZConnent.dto.club.response.MyClubSimpleResponse;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyClubService {
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

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

    private List<Club> getClubsByMember(Member member) {
        return member.getClubMembers().stream()
                .map(cm -> cm.getClub())
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

    public MyClubDetailResponse getMyClubDetail(String email, Long clubId) {
        Member member = getMemberByEmail(email);
        Club club = getClubByMemberAndId(member, clubId);

        MyClubDetailResponse.MyClubCultureDto cultureDto = convertToCultureDto(club);
        List<MyClubDetailResponse.MyClubMemberDto> memberDtos = convertToMemberDtos(club);

        return MyClubDetailResponse.builder()
                .clubId(club.getClubId())
                .title(club.getTitle())
                .meetingDate(club.getMeetingDate())
                .content(club.getContent())
                .currentParticipant(club.getClubMembers().size())
                .culture(cultureDto)
                .members(memberDtos)
                .build();
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    private Club getClubByMemberAndId(Member member, Long clubId) {
        return member.getClubMembers().stream()
                .map(cm -> cm.getClub())
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

    private ClubMember getClubMemberByMemberAndId(Member member, Long clubId) {
        return member.getClubMembers().stream()
                .filter(cm -> cm.getClub().getClubId().equals(clubId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 가입된 모임이 아닙니다."));
    }
}
