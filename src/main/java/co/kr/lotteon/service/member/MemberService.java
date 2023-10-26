package co.kr.lotteon.service.member;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.member.TermsDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.member.TermsEntity;
import co.kr.lotteon.mapper.MemberMapper;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.member.TermsRepository;
import co.kr.lotteon.security.MyUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    public MemberDTO updateMember(MemberDTO dto){
        memberMapper.updateMember(dto);
        MemberDTO memberDTO = memberMapper.selectUpdatedMember(dto.getUid());
        return memberDTO;
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
        log.info(" ----- selectRoleByUid() ----- ");
        MemberEntity entity = memberRepository.findById(uid).orElse(null);
        MemberDTO member = entity.toDTO();
        member.setPass("");
        int level = member.getLevel();
        log.info(" - level : " + level);

        return level;
    }
    
    // 계정 정보 조회
    public MemberDTO MyAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDTO memberDTO = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof MyUserDetails) {
                MyUserDetails myUserDetails = (MyUserDetails) userDetails;
                MemberEntity memberEntity = myUserDetails.getMember();
                memberDTO = memberEntity.toDTO();

            }
        }
        return memberDTO;

    }
}
