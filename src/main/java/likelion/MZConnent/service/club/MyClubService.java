package likelion.MZConnent.service.club;

import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.response.ClubSimpleResponse;
import likelion.MZConnent.dto.club.response.MyClubSimpleResponse;
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

    public MyClubSimpleResponse getMyClubs(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<Club> clubList = getClubsByMember(member);

        List<MyClubSimpleResponse.MyClubSimpleDto> myClubs = clubList.stream()
                .map(this::convertToDto)
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

    private MyClubSimpleResponse.MyClubSimpleDto convertToDto(Club club) {
        return MyClubSimpleResponse.MyClubSimpleDto.builder()
                .clubId(club.getClubId())
                .title(club.getTitle())
                .cultureName(club.getCulture().getName())
                .meetingDate(club.getMeetingDate())
                .currentParticipant(club.getClubMembers().size())
                .maxParticipant(club.getMaxParticipant())
                .build();
    }
}
