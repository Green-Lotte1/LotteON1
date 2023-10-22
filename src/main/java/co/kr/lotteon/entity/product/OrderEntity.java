package co.kr.lotteon.entity.product;

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
    @JoinColumn(name = "prodCate1")
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
}
