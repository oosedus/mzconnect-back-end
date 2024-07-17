package likelion.MZConnent.service;

import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.domain.member.Role;
import likelion.MZConnent.dto.member.CreateMemberRequest;
import likelion.MZConnent.jwt.blacklist.AccessTokenBlackList;
import likelion.MZConnent.jwt.token.TokenProvider;
import likelion.MZConnent.jwt.token.refreshToken.RefreshTokenList;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$"; // 최소 8자리 + 영어, 숫자, 특수문자 모두 포함 조건을 만족하기 위한 정규표현식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AccessTokenBlackList accessTokenBlackList;
    private final RefreshTokenList refreshTokenList;

    // 회원가입
    public Long createUser(CreateMemberRequest request) {
        // 비밀번호 정책에 맞는지 점검
        checkPasswordPolicy(request.getPassword());

        // 이미 등록된 이메일인지 점검
        if (memberRepository.existsByEmail(request.getEmail())) {
            log.info("이미 등록된 이메일={}", request.getEmail());
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .realname(request.getRealname())
                .username(request.getUsername())
                .gender(request.getGender())
                .age(request.getAge())
                .role(Role.USER)
                .build();

        try {
            return memberRepository.save(member).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 비밀번호 정책에 맞는지 점검하는 함수
    private void checkPasswordPolicy(String password) {
        if (PASSWORD_PATTERN.matcher(password).matches()) {
            return;
        }

        log.info("비밀번호 정책 미달");
        throw new IllegalArgumentException("비밀번호는 최소 8자리여야하고 영어, 숫자, 특수문자를 포함해야 합니다.");
    }

}
