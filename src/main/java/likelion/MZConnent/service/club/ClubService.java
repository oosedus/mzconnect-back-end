package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.club.Club;
import likelion.MZConnent.domain.club.ClubMember;
import likelion.MZConnent.domain.club.ClubRole;
import likelion.MZConnent.domain.club.RegionCategory;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.request.CreateClubRequest;
import likelion.MZConnent.dto.club.response.CreateClubResponse;
import likelion.MZConnent.repository.club.ClubMemberRepository;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.club.RegionCategoryRepository;
import likelion.MZConnent.repository.culture.CultureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final CultureRepository cultureRepository;
    private final RegionCategoryRepository regionCategoryRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;


    @Transactional
    public CreateClubResponse createClub(CreateClubRequest request, Member member) {

        Culture culture = cultureRepository.findById(request.getCultureId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문화입니다."));
        RegionCategory region = regionCategoryRepository.findById(request.getRegionId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다."));

        Club club = Club.builder()
                .title(request.getTitle())
                .meetingDate(request.getMeetingDate())
                .createdDate(LocalDateTime.now())
                .maxParticipant(request.getMaxParticipant())
                .currentParticipant(0)
                .content(request.getContent())
                .genderRestriction(request.getGenderRestriction())
                .ageRestriction(request.getAgeRestriction())
                .culture(culture)
                .region(region)
                .status("OPEN")
                .build();

        try {
            clubRepository.save(club);
        } catch (Exception e) {
            throw new IllegalArgumentException("클럽 생성에 실패했습니다.");
        }

        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(member)
                .clubRole(ClubRole.LEADER)
                .build();

        try {
            clubMemberRepository.save(clubMember);
        } catch (Exception e) {
            throw new IllegalArgumentException("클럽 멤버 생성에 실패했습니다.");
        }

        return new CreateClubResponse(
                club.getCulture().getCultureId(),
                club.getRegion().getRegionId(),
                club.getTitle(),
                club.getMeetingDate(),
                club.getCreatedDate(),
                club.getMaxParticipant(),
                club.getContent(),
                club.getGenderRestriction(),
                club.getAgeRestriction(),
                club.getStatus()
        );
    }

}
