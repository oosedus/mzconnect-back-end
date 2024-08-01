package likelion.MZConnent.api.main;

import likelion.MZConnent.dto.main.response.MainInfoResponse;
import likelion.MZConnent.service.main.MainInfoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MainInfoController {
    private final MainInfoService mainInfoService;

    @GetMapping("/api/main")
    public ResponseEntity<MainInfoResponse> getMainInfo() {
        MainInfoResponse mainInfo = mainInfoService.getMainInfo();
        log.info("메인 정보 조회: {}", mainInfo);

        return ResponseEntity.ok(mainInfo);
    }
}
