package co.kr.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDTO {

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 10;

    @Builder.Default
    private int cate1 = 10;

    @Builder.Default
    private int cate2 = 10;


    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }
}