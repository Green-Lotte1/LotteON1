package co.kr.lotteon.mapper;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    //public void insertMember(MemberDTO dto);
    //public TermsDTO selectTerms();
    public int countUid(String uid);
}
