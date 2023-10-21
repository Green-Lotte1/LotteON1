package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.member.MemberDTO;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class OrderDTO {

    private int ordNo;
    private MemberDTO ordUid;
    private int ordPrice;
    private int ordCount;
    private int ordDiscount;
    private int ordDelivery;
    private int savePoint;
    private int usedPoint;
    private int ordTotPrice;
    private String recipName;
    private String recipHp;
    private String recipAddr1;
    private String recipAddr2;
    private int ordPayment;
    private int ordComplete;
    private LocalDateTime ordDate;
}
