package likelion.MZConnent.service.club;

import jakarta.transaction.Transactional;
import likelion.MZConnent.domain.club.RegionCategory;
import likelion.MZConnent.dto.club.RegionCategoryDto;
import likelion.MZConnent.dto.club.response.RegionCategoryResponse;
import likelion.MZConnent.repository.club.RegionCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionCategoryService {
    private final RegionCategoryRepository regionCategoryRepository;

    public RegionCategoryResponse getAllRegionCategories() {
        List<RegionCategory> regionCategories = regionCategoryRepository.findAll();

        return new RegionCategoryResponse(regionCategories);
    }
}
