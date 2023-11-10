package study.board2.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PageDto {

    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;

    public static PageDto of(Page page) {
        return new PageDto(page.getNumber()+1, page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
