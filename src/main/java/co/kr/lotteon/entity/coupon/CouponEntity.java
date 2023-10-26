package co.kr.lotteon.entity.coupon;

import co.kr.lotteon.dto.coupon.CouponDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_coupon")
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int coupNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private     MemberEntity     uid;
    private     String           coupName;
    private     int              type;
    private     int              coupDiscount;
    private     int              coupLimit;
    private     int              coupStock;
    private     int              coupPeriod;
    @CreationTimestamp
    private     LocalDateTime    rdate;
    private     LocalDateTime    wdate;

    public CouponDTO toDTO() {
        return CouponDTO.builder()
                .coupNo(coupNo)
                .uid(uid.toDTO())
                .coupName(coupName)
                .type(type)
                .coupDiscount(coupDiscount)
                .coupLimit(coupLimit)
                .coupStock(coupStock)
                .coupPeriod(coupPeriod)
                .rdate(rdate)
                .wdate(wdate)
                .build();
    }

}
