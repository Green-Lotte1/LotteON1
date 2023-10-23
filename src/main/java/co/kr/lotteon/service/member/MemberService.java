package co.kr.lotteon.service.member;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.member.TermsDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.member.TermsEntity;
import co.kr.lotteon.mapper.MemberMapper;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.member.TermsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private MemberMapper memberMapper;

    public void insert(MemberDTO dto){
        dto.setPass(passwordEncoder.encode(dto.getPass()));
        // DTO를 Entity로 변환
        MemberEntity entity = dto.toEntity();

        memberRepository.save(entity);
    }

    public TermsDTO findTerms(){
        TermsEntity entity = termsRepository.findById(1).get();
        TermsDTO dto = entity.toDTO();
        return dto;
    }

    public int countUid(String uid){
        int result = memberMapper.countUid(uid);
        log.info("countUid : "+result);
        return result;
    }
    public int countEmail(String email){
        int result = memberRepository.countByEmail(email);
        log.info("countUid : "+result);
        return result;
    }
    public int countHp(String hp){
        int result = memberRepository.countByHp(hp);
        log.info("countUid : "+result);
        return result;
    }

    public MemberDTO selectMemberByUid(String uid){
        return memberRepository.findById(uid).orElse(null).toDTO();
    }

    public MemberDTO selectMemberOrderInfoByUid(String uid){

        MemberDTO memberDTO = new MemberDTO();

        memberDTO = memberMapper.selectMemberOrderInfoByUid(uid).toDTO();

        return memberDTO;
    }



    public int selectRoleByUid(String uid) {
        MemberEntity member = memberRepository.findById(uid).orElse(null);
        member.setPass("");
        int level = member.getLevel();

        return level;
    }
}
