package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.OrderDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "km_product_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ordNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordUid")
    private MemberEntity ordUid;
    private int ordPrice;
    private int ordCount;
    private int ordDiscount;
    private int ordDelivery;
    private int savePoint;
    private int usedPoint;
    private int ordTotPrice;
    private String recipName;
    private String recipHp;
    private String recipZip;
    private String recipAddr1;
    private String recipAddr2;
    private int ordPayment;
    private int ordComplete;
    private LocalDateTime ordDate;

    public OrderDTO toDTO(){
        return OrderDTO.builder()
                .ordNo(ordNo)
                .member(ordUid.toDTO())
                .ordPrice(ordPrice)
                .ordCount(ordCount)
                .ordDiscount(ordDiscount)
                .ordDelivery(ordDelivery)
                .savePoint(savePoint)
                .usedPoint(usedPoint)
                .ordTotPrice(ordTotPrice)
                .recipName(recipName)
                .recipHp(recipHp)
                .recipZip(recipZip)
                .recipAddr1(recipAddr1)
                .recipAddr2(recipAddr2)
                .ordPayment(ordPayment)
                .ordComplete(ordComplete)
                .ordDate(ordDate)
                .build();
    }
}
