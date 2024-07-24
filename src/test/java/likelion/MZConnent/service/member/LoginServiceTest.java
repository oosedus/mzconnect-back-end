package likelion.MZConnent.service.member;

import likelion.MZConnent.domain.member.Age;
import likelion.MZConnent.domain.member.Gender;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.member.CreateMemberRequest;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest
@Slf4j
class LoginServiceTest {
    @Autowired
    LoginService loginService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void createMember() throws Exception {
        //given
        CreateMemberRequest request = CreateMemberRequest.builder()
                .email("tes1t@test.ac.kr")
                .password("test1234@!!")
                .realname("테스트")
                .age(Age.FOURTH_GRADE)
                .gender(Gender.FEMALE)
                .instagramId("insta")
                .facebookId("facebook")
                .selfIntroductions(new ArrayList<>(Arrays.asList(1L, 2L, 3L)))
                .build();

        //when
        Long memberId = loginService.createUser(request);
        Member member = memberRepository.findById(memberId).get();

        //then
        log.info("member: {}", member);
        Assertions.assertEquals(memberId, member.getId());
    }
}