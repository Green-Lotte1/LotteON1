package co.kr.lotteon.service;

import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.entity.MemberEntity;
import co.kr.lotteon.mapper.MemberMapper;
import co.kr.lotteon.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberMapper memberMapper;

    public void insert(MemberDTO dto){
        dto.setPass(passwordEncoder.encode(dto.getPass()));
        // DTO를 Entity로 변환
        MemberEntity entity = dto.toEntity();

        memberMapper.insertMember(dto);
    }

}
