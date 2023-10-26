package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.coupon.CouponDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouponMapper {

    public List<CouponDTO> myCouponList(@Param("uid")String uid, @Param("pg")int pg, @Param("status")String status);

    public int myCouponTotal(@Param("uid")String uid, @Param("status")String status);
}
