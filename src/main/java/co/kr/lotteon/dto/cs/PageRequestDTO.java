package co.kr.lotteon.dto.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Log4j2
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int pg =1;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private String group = "notice";

    @Builder.Default
    private String cate1 = "101";

    @Builder.Default
    private String cate2 = "0";

    @Builder.Default
    private int no = 0;

    @Builder.Default
    private String type = "write";

    @Builder.Default
    private String success = "0";

    private String uid;
    private String title;
    private String content;

    public Pageable getPageable(String sort) {
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }
}