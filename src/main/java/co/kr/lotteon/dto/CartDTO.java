package co.kr.lotteon.dto;

import co.kr.lotteon.entity.CartEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class CartDTO {

    @Id
    private int cartNo;
    private String uid;
    private int prodNo;
    private int count;
    private int cartPrice;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private LocalDateTime rdate;

    public CartEntity toEntity() {
        return CartEntity.builder()
                .cartNo(cartNo)
                .uid(uid)
                .prodNo(prodNo)
                .count(count)
                .cartPrice(cartPrice)
                .point(point)
                .delivery(delivery)
                .total(total)
                .rdate(rdate)
                .build();
    }


    //추가 필드
    private String thumb1;
    private int prodCate1;
    private int prodCate2;
    private String prodName;
    private String descript;
    private int orgPrice;
}
