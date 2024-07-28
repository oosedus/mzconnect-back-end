package likelion.MZConnent.service.culture;

import likelion.MZConnent.dto.culture.response.CultureCategoryResponse;
import likelion.MZConnent.dto.culture.response.CultureDetailResponse;
import likelion.MZConnent.repository.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CultureCategoryServiceTest {
    @Autowired
    CultureCategoryService cultureCategoryService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CultureService cultureService;

    @Test
    public void getCultureCategory() throws Exception {
        //given

        //when
        CultureCategoryResponse all = cultureCategoryService.getAllCultureCategories();

        //then
        Assertions.assertEquals(all.getCultureCategories().size(), 5);
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
}