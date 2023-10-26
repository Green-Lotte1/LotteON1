package co.kr.lotteon.repository.coupon;

import co.kr.lotteon.entity.coupon.MemberCouponEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Integer> {

    public Page<MemberCouponEntity> findByUid(MemberEntity uid, Pageable pageable);

    @Query(value = "SELECT count(*) FROM km_member_coupon " +
            "WHERE (ordItemNo IS NULL AND `exp` > CURRENT_DATE())",
            nativeQuery = true)
    public int countByUseableCoupon();

}
