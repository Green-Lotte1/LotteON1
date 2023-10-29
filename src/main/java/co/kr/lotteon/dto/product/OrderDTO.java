package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.Utils;
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

    // 추가필드
    private int    count;
    private int    total;
    private int    prodNo;
    private String prodCompany;
    private String prodName;
    private int    prodCate1;
    private int    prodCate2;
    private String thumb1;

    public String totalWithComma() {
        return Utils.comma(total);
    }


    public String getOrdPaymentName(){

        String ordPaymentName = null;

        switch (ordPayment){
            case(11): ordPaymentName = "신용카드";
            break;
            case(12): ordPaymentName = "체크카드";
            break;
            case(21): ordPaymentName = "실시간 계좌이체";
            break;
            case(22): ordPaymentName = "무통장 입금";
            break;
            case(31): ordPaymentName = "휴대폰결제";
            break;
            case(32): ordPaymentName = "카카오페이";
            break;
            
        }
        return ordPaymentName;
    }
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
