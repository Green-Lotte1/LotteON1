package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.ProdCate2DTO;
import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
@Table(name = "km_product_cate1")
public class ProdCate2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate1")
    private ProdCate1Entity cate1;
    private int cate2;
    private String c2Name;

    public ProdCate2DTO toDTO() {
        return ProdCate2DTO.builder()
                .no(no)
                .cate1(cate1.toDTO())
                .cate2(cate2)
                .c2Name(c2Name)
                .build();
    }
}
