package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.member.PointDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PointMapper {


    public int insertPoint(@Param("uid")String uid, @Param("ordNo")int ordNo, @Param("point")int point);

    public void minusMemberPoint(@Param("uid")String uid, @Param("point")int point);

    public List<PointDTO> myPointList(@Param("uid")String uid, @Param("type1")String type1, @Param("type2")String type2, @Param("pg")int pg);

    public int myPointCount(@Param("uid")String uid, @Param("type1")String type1, @Param("type2")String type2);

    public void insertUsedPoint(@Param("uid")String uid, @Param("ordNo")int ordNo, @Param("note")String note,@Param("point")int point);

    public int myPointTotal(@Param("uid")String uid);

}
