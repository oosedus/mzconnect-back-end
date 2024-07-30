package likelion.MZConnent.dto.review.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
public class SaveReviewRequest {
    private String title;
    private String content;

    @Builder
    public SaveReviewRequest(String title, String content, List<String> reviewImageUrls) {
        this.title = title;
        this.content = content;
    }
}
