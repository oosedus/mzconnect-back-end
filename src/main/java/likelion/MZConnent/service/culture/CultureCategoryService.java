package likelion.MZConnent.service.culture;


import likelion.MZConnent.domain.culture.CultureCategory;
import likelion.MZConnent.dto.culture.CultureCategoryResponse;
import likelion.MZConnent.repository.culture.CultureCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CultureCategoryService {
    private final CultureCategoryRepository cultureCategoryRepository;

    public CultureCategoryResponse getAllCultureCategories() {
        List<CultureCategory> cultureCategories = cultureCategoryRepository.findAll();
        log.info("cultureCategories: {}", cultureCategories);
        return new CultureCategoryResponse(cultureCategories);
    }
}
