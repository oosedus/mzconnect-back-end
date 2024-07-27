package likelion.MZConnent.service.image;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberProfileService {
    private final S3ImageService s3ImageService;
    private final MemberRepository memberRepository;

    public String updateProfileImage(String email, MultipartFile image) throws IOException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 가입된 회원이 없습니다."));
        String imageUrl = s3ImageService.upload(image, "profile");

        // 기존 프로필 이미지 삭제
        deleteProfileImage(member, email);

        member.setProfileImageUrl(imageUrl);
        memberRepository.save(member);
        return imageUrl;
    }

    // 원래 S3 에 있는 해당 유저의 프로필 사진 삭제
    public void deleteProfileImage(Member member, String email) {
        String imageUrl = member.getProfileImageUrl();
        // 기본 프로필이 아닌 경우에만 삭제
        if (!imageUrl.equals(Member.DEFAULT_PROFILE_IMAGE_URL)) {
            s3ImageService.delete(imageUrl);
        }
    }


}
