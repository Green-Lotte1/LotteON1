package co.kr.lotteon.entity;

import co.kr.lotteon.dto.CartDTO;
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
    private String uid;
    private int prodNo;
    private int count;
    private int cartPrice;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    @CreationTimestamp
    private LocalDateTime rdate;

    public CartDTO toDTO() {
        return CartDTO.builder()
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

}
