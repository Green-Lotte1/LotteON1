package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.member.PointDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PointMapper {


    public int insertPoint(@Param("uid")String uid, @Param("ordNo")int ordNo, @Param("point")int point);

    public void minusMemberPoint(@Param("uid")String uid, @Param("point")int point);


}
