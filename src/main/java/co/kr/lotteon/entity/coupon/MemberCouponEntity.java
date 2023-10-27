package co.kr.lotteon.entity.coupon;

import co.kr.lotteon.dto.coupon.MemberCouponDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.product.OrderItemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_member_coupon")
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private     int              no;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private     MemberEntity     uid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupNo")
    private     CouponEntity     coupNo;
    @ManyToOne
    @JoinColumn(name = "ordItemNo")
    private     OrderItemEntity  ordItemNo;
    @CreationTimestamp
    private     LocalDate        rdate;
    private     LocalDate        exp;

}