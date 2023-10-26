package co.kr.lotteon.dto.admin.cs;

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
    private String group = null;
    @Builder.Default
    private String cate1 = null;
    @Builder.Default
    private String cate2 = null;

    @Builder.Default
    private String success = "0";

    @Builder.Default
    private int no = 0;
    @Builder.Default
    private String noSelect = "/";

    @Builder.Default
    private int parent = 0;
    private String uid;
    private String title;
    private String content;
    @Builder.Default
    private String status = "useable";
    // 사용가능: useable, 사용한 쿠폰: used, 사용만료: expired

    public Pageable getPageable(String sort) {
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }
}