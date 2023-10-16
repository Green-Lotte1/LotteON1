package co.kr.lotteon.dto.product;

import co.kr.lotteon.entity.product.ProdCate2Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ProdCate2DTO {

    @Id
    private int no;
    private ProdCate1DTO cate1;
    private int cate2;
    private String c2Name;

    public ProdCate2Entity toEntity() {
        return ProdCate2Entity.builder()
                .no(no)
                .cate1(cate1.toEntity())
                .cate2(cate2)
                .c2Name(c2Name)
                .build();
    }
}
