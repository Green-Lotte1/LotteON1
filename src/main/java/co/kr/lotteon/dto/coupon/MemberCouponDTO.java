package co.kr.lotteon.dto.coupon;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.product.OrderItemDTO;
import co.kr.lotteon.entity.coupon.CouponEntity;
import co.kr.lotteon.entity.coupon.MemberCouponEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.product.OrderItemEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberCouponDTO {

    private     int              no;
    private     MemberDTO        uid;
    private     CouponDTO        coupNo;
    private     OrderItemDTO     ordItemNo;
    private     LocalDate        rdate;
    private     LocalDate        exp;

    public MemberCouponEntity toEntity() {
        return MemberCouponEntity
                .builder()
                .no(no)
                .uid(uid.toEntity())
                .coupNo(coupNo.toEntity())
                .ordItemNo(ordItemNo != null ? ordItemNo.toEntity() : null)
                .rdate(rdate)
                .exp(exp)
                .build();
    }

}
