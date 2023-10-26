package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.member.PointDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {


    public int insertPoint(String uid, int ordNo, int point);

    public void minusMemberPoint(String uid, int point);


}
