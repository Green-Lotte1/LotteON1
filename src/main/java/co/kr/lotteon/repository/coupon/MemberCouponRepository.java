package co.kr.lotteon.repository.coupon;

import co.kr.lotteon.entity.coupon.MemberCouponEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Integer> {

}
