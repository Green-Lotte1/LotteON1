package co.kr.lotteon.mapper;


import co.kr.lotteon.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public void insertMember(MemberDTO dto);

}
