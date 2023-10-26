package co.kr.lotteon.mapper;


import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    //public void insertMember(MemberDTO dto);
    //public TermsDTO selectTerms();
    public int countUid(String uid);

    public MemberEntity selectMemberOrderInfoByUid(String uid);
    public void updateMember(MemberDTO dto);
    public MemberDTO selectUpdatedMember(String uid);

}
