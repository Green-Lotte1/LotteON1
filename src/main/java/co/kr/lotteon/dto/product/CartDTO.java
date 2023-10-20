package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.product.CartEntity;
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
    private MemberDTO uid;
    private ProductDTO prodNo;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private LocalDateTime rdate;

    public CartEntity toEntity() {
        return CartEntity.builder()
                .cartNo(cartNo)
                .uid(uid.toEntity())
                //.prodNo(prodNo)
                .count(count)
                .price(price)
                .point(point)
                .delivery(delivery)
                .total(total)
                .rdate(rdate)
                .build();
    }

    public int getSavePoint(){
        return ((price*count)/100)*point;
    }
    public int discountingPrice(){
        return price - ((price/100)*discount);
    }

}
