package co.kr.lotteon.repository.coupon;

import co.kr.lotteon.entity.coupon.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {

}
