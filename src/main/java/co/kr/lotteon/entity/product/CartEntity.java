package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.CartDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_product_cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private MemberEntity uid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodNo")
    private ProductEntity prodNo;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    @CreationTimestamp
    private LocalDateTime rdate;

    public CartDTO toDTO() {
        return CartDTO.builder()
                .cartNo(cartNo)
                .uid(uid.toDTO())
                .prodNo(prodNo.toDTO())
                .count(count)
                .price(price)
                .point(point)
                .delivery(delivery)
                .total(total)
                .rdate(rdate)
                .build();
    }

}
