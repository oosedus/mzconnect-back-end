package likelion.MZConnent.dto.paging.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageContentResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int size;

    @Builder
    public PageContentResponse(List<T> content, int totalPages, long totalElements, int size) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
    }
}
