package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class MyUserController {

    @Autowired
    private MemberService memberService;
    @ResponseBody
    @GetMapping("/my/getUser")
    public MemberDTO userData(){
        MemberDTO memberDTO = memberService.MyAccount();
        memberDTO.setPass("****");
        return memberDTO;
    }

}
