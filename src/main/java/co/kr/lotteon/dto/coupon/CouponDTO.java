package co.kr.lotteon.dto.coupon;

import co.kr.lotteon.dto.Utils;
import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.coupon.CouponEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponDTO {

    private     int              coupNo;

    private     MemberDTO        uid;
    private     String           coupName;
    private     int              type;
    private     int              coupDiscount;
    private     int              coupLimit;
    private     int              coupStock;
    private     int              coupPeriod;
    
    private     LocalDateTime    rdate;
    private     LocalDateTime    wdate;

    public CouponEntity toEntity() {
        return CouponEntity.builder()
                .coupNo(coupNo)
                .uid(uid.toEntity())
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

    public String getLimitWithComma() {
        return Utils.comma(coupLimit);
    }

    public String getDiscountWithComma() {
        return Utils.comma(coupDiscount);
    }
}