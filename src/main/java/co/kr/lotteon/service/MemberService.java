package co.kr.lotteon.service;

import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.entity.MemberEntity;
import co.kr.lotteon.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(MemberDTO dto){

        dto.setKm_pass1(passwordEncoder.encode(dto.getKm_pass1()));
        // DTO를 Entity로 변환
        MemberEntity entity = dto.toEntity();

        // DB insert
        memberRepository.save(entity);
    }

}
