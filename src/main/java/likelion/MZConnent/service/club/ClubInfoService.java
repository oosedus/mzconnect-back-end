package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.LeaderDto;
import likelion.MZConnent.dto.club.SelfIntroductionDto;
import likelion.MZConnent.dto.club.response.ClubDetailResponse;
import likelion.MZConnent.dto.club.response.ClubSimpleResponse;
import likelion.MZConnent.dto.club.response.PageContentResponse;
import likelion.MZConnent.repository.club.ClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import likelion.MZConnent.domain.club.Club;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClubInfoService {
    private final ClubRepository clubRepository;

    // 로그인하지 않은 사용자
    public ClubDetailResponse getClubDetail(Long clubId) {
        return buildClubDetailResponse(clubId, null);
    }

    // 로그인한 사용자
    public ClubDetailResponse getClubDetail(Long clubId, Member member) {
        return buildClubDetailResponse(clubId, member);
    }

    private ClubDetailResponse buildClubDetailResponse(Long clubId, Member member) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));

        List<LeaderDto> leaderResponses = club.getClubMembers().stream()
                .filter(cm -> cm.getClubRole() == ClubRole.LEADER)
                .map(cm -> LeaderDto.builder()
                        .username(cm.getMember().getUsername())
                        .profileImageUrl(cm.getMember().getProfileImageUrl())
                        .selfIntroductions(cm.getMember().getSelfIntroductions().stream()
                                .map(si -> SelfIntroductionDto.builder()
                                        .cultureCategoryId(si.getCultureCategory().getId())
                                        .name(si.getCultureCategory().getName())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        String registrationStatus = "신청하기";

        // 로그인한 사용자가 해당 모임에 가입되어 있는지 확인
        if (member != null) {
            boolean isMemberJoined = club.getClubMembers().stream()
                    .anyMatch(cm -> {
                        return cm.getMember().getId().equals(member.getId());
                    });
            registrationStatus = isMemberJoined ? "신청완료" : "신청하기";
        }


        return ClubDetailResponse.builder()
                .clubId(club.getClubId())
                .title(club.getTitle())
                .meetingDate(club.getMeetingDate())
                .createdDate(club.getCreatedDate())
                .content(club.getContent())
                .genderRestriction(club.getGenderRestriction())
                .ageRestriction(club.getAgeRestriction())
                .cultureImageUrl(club.getCulture().getCultureImageUrl())
                .cultureName(club.getCulture().getName())
                .regionName(club.getRegion().getName())
                .currentParticipant(club.getCurrentParticipant())
                .maxParticipant(club.getMaxParticipant())
                .registrationStatus(registrationStatus)
                .leader(leaderResponses)
                .build();
    }

    public PageContentResponse<ClubSimpleResponse> getClubList(long cultureCategoryId, long regionId, Pageable pageable) {
        Page<Club> clubs = clubRepository.findAllByFilters(
                cultureCategoryId,
                regionId,
                pageable);

        return new PageContentResponse<>(
                clubs.stream()
                        .map(club -> ClubSimpleResponse.builder()
                                .clubId(club.getClubId())
                                .title(club.getTitle())
                                .regionId(club.getRegion().getRegionId())
                                .regionName(club.getRegion().getName())
                                .cultureCategoryId(club.getCulture().getCultureCategory().getId())
                                .cultureName(club.getCulture().getName())
                                .leaderProfileImage(getLeaderProfileImage(club))
                                .meetingDate(club.getMeetingDate())
                                .createdDate(club.getCreatedDate())
                                .genderRestriction(club.getGenderRestriction().name())
                                .ageRestriction(club.getAgeRestriction().name())
                                .currentParticipant(club.getCurrentParticipant())
                                .maxParticipant(club.getMaxParticipant())
                                .build())
                        .collect(Collectors.toList()),
                clubs.getTotalPages(),
                clubs.getTotalElements(),
                pageable.getPageSize()
        );
    }

    private String getLeaderProfileImage(Club club) {
        return club.getClubMembers().stream()
                .filter(cm -> cm.getClubRole() == ClubRole.LEADER)
                .findFirst()
                .map(cm -> cm.getMember().getProfileImageUrl())
                .orElse(null);
    }
}
