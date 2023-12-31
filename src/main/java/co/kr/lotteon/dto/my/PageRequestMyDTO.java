package co.kr.lotteon.dto.my;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestMyDTO {
    @Builder.Default
    private int pg = 1;
    @Builder.Default
    private int size = 10;

    private String uid;
    @Builder.Default
    private String type1 = "day";
    @Builder.Default
    private String type2 = "15";

    public Pageable getPageable(String sort) {
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }
}