package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.OrderItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "km_product_order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    @ManyToOne
    @JoinColumn(name = "ordNo")
    private OrderEntity   ordNo;
    @ManyToOne
    @JoinColumn(name = "prodNo")
    private ProductEntity prodNo;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;

    public OrderItemDTO toDTO() {
        return OrderItemDTO.builder()
                .no(no)
                .count(count)
                .price(price)
                .discount(discount)
                .point(point)
                .delivery(delivery)
                .total(total)
                .build();
    }
}