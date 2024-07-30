package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.club.*;
import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.club.request.CreateClubRequest;
import likelion.MZConnent.dto.club.response.CreateClubResponse;
import likelion.MZConnent.repository.club.ClubMemberRepository;
import likelion.MZConnent.repository.club.ClubRepository;
import likelion.MZConnent.repository.club.RegionCategoryRepository;
import likelion.MZConnent.repository.culture.CultureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
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
        culture.setClubCount(culture.getClubCount() + 1);
        RegionCategory region = regionCategoryRepository.findById(request.getRegionId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지역입니다."));

        Club club = Club.builder()
                .title(request.getTitle())
                .meetingDate(request.getMeetingDate())
                .createdDate(LocalDateTime.now())
                .maxParticipant(request.getMaxParticipant())
                .currentParticipant(1)
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

    @Transactional
    public void joinClub(Long clubId, Member member) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 클럽입니다."));

        validateJoinClub(club, member);

        ClubMember clubMember = ClubMember.builder()
                .club(club)
                .member(member)
                .clubRole(ClubRole.MEMBER)
                .build();

        clubMemberRepository.save(clubMember);
        club.setCurrentParticipant(club.getCurrentParticipant() + 1);

        if(club.getCurrentParticipant() == club.getMaxParticipant()) {
            club.setStatus("CLOSE");
        }
    }

    private void validateJoinClub(Club club, Member member) {
        if (clubMemberRepository.findByClubAndMember(club, member).isPresent()) {
            throw new IllegalArgumentException("이미 가입한 클럽입니다.");
        }

        if (club.getCurrentParticipant() >= club.getMaxParticipant()) {
            throw new IllegalArgumentException("정원이 초과되어 가입할 수 없습니다.");
        }

        if (club.getAgeRestriction() != AgeRestriction.ALL && !(club.getAgeRestriction().equals(member.getAge()))) {
            throw new IllegalArgumentException("나이 제한으로 가입할 수 없습니다.");
        }

        if (club.getGenderRestriction() != GenderRestriction.ALL && !(club.getGenderRestriction().equals(member.getGender()))) {
            throw new IllegalArgumentException("성별 제한으로 가입할 수 없습니다.");
        }
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void closeClub() {
        clubRepository.findAllByStatus("OPEN").stream()
                .filter(club -> club.getMeetingDate().isBefore(LocalDate.now()))
                .forEach(club -> {
                    club.setStatus("CLOSE");
                });
    }
}
