package co.kr.lotteon.mapper;


import co.kr.lotteon.dto.member.TermsDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    //public void insertMember(MemberDTO dto);
    //public TermsDTO selectTerms();
    public int countUid(String uid);
    public List<TermsDTO> selectPolicy();
    public MemberEntity selectMemberOrderInfoByUid(String uid);
}
