package co.kr.lotteon.controller.member;

import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/join")
    public String join(){
        return "/member/join";
    }
    @GetMapping("/member/login")
    public String login(){
        return "/member/login";
    }
    @GetMapping("/member/register")
    public String register(){
        return "/member/register";
    }
    @PostMapping ("/member/register")
    public String register(MemberDTO dto){

        return "/member/register";
    }
    @GetMapping("/member/registerSeller")
    public String registerSeller(){
        return "/member/registerSeller";
    }
    @GetMapping("/member/signup")
    public String signup(){
        return "/member/signup";
    }
}
