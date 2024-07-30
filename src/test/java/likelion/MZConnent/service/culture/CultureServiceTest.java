package likelion.MZConnent.service.culture;

import likelion.MZConnent.domain.culture.Culture;
import likelion.MZConnent.domain.member.Member;
import likelion.MZConnent.dto.culture.response.CultureCategoryResponse;
import likelion.MZConnent.dto.culture.response.CultureDetailResponse;
import likelion.MZConnent.repository.culture.CultureRepository;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
class CultureServiceTest {
    @Autowired
    CultureCategoryService cultureCategoryService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CultureService cultureService;

    @Autowired
    CultureRepository cultureRepository;

    @Test
    public void getCultureCategory() throws Exception {
        //given

        //when
        CultureCategoryResponse all = cultureCategoryService.getAllCultureCategories();

        //then
//        Assertions.assertEquals(all.getCultureCategories().size(), 5);
    }

    @Test
    public void getCultureDetailInfo() throws Exception {
        //given
        Long cultureId = 1L;

        //when
        CultureDetailResponse cultureDetailInfo = cultureService.getCultureDetailInfo(cultureId);

        //then
        log.info("문화: {}", cultureDetailInfo);
        Assertions.assertEquals(cultureDetailInfo.getCultureId(), 1L);
    }

    @Test
//    @Rollback(value = false)
    public void addCultureInterest() throws Exception {
        //given
        Culture culture = cultureRepository.findById(4L).get();
        Member member  = memberRepository.findById(2L).get();

        //when
        int interestCount = cultureService.addCultureInterest(member.getEmail(), culture.getCultureId());

        //then
        log.info("관심수: {}", interestCount);

        Assertions.assertEquals(member.getCultureInterests().size(), 2);
        Assertions.assertEquals(culture.getCultureInterests().size(), 2);
        Assertions.assertEquals(culture.getInterestCount(), interestCount);


    }
}