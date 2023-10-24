package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.product.OrderEntity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class OrderDTO {

    @Id
    private int ordNo;
    private String ordUid;
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

    private MemberDTO member;
/*
    public OrderEntity toEntity(){
        return OrderEntity.builder()
                .ordNo(ordNo)
                .(ordUid.toDTO())
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
    }*/
}
