package co.kr.lotteon.dto.product;

import co.kr.lotteon.entity.product.ProdCate1Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ProdCate1DTO {

    @Id
    private int cate1;
    private String c1Name;
    private String c1Icon;

    public ProdCate1Entity toEntity() {
        return ProdCate1Entity.builder()
                .cate1(cate1)
                .c1Name(c1Name)
                .c1Icon(c1Icon)
                .build();
    }
}
