package co.kr.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

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
    private int prodCate1 = 0;

    @Builder.Default
    private int prodCate2 = 0;

    @Builder.Default
    private String type = "default";

    @Builder.Default
    private int prodNo = 0;

    @Builder.Default
    private int input = 1;

    @Builder.Default
    private int result = 0;

    @Builder.Default
    private String selectedItemNos = "/";

    @Builder.Default
    private int ordNo = 0;

    @Builder.Default
    private int revNo = 0;


    ///////////////////////////////////////////////////////
    ///////////// SEARCH
    ///////////////////////////////////////////////////////
    @Builder.Default
    private String keyword = "";
    @Builder.Default
    private Boolean chkProdName = false;
    @Builder.Default
    private Boolean chkProdDesc = false;
    @Builder.Default
    private Boolean chkProdPrice = false;
    private String min;
    private String max;

    /*@Builder.Default
    private OrderDTO orderDTO = null;*/


    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }
}
