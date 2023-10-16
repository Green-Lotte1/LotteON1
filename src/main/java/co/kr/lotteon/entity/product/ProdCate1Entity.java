package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.ProdCate1DTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Builder
@Table(name = "km_product_cate1")
public class ProdCate1Entity {

    @Id
    private int cate1;
    private String c1Name;
    private String c1Icon;

    public ProdCate1DTO toDTO() {
        return ProdCate1DTO.builder()
                .cate1(cate1)
                .c1Name(c1Name)
                .c1Icon(c1Icon)
                .build();
    }
}
