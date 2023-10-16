package co.kr.lotteon.mapper;


import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.dto.member.TermsDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public void insertMember(MemberDTO dto);
    public TermsDTO selectTerms();

}
