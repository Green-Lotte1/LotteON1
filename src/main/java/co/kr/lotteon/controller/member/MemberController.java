package co.kr.lotteon.controller.member;

import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/join")
    public String join(){
        log.info("join...1");

        return "/member/join";
    }
    @GetMapping("/member/login")
    public String login(){
        log.info("login...1");
        return "/member/login";
    }
    @GetMapping("/member/register")
    public String register(){
        log.info("register...1");
        return "/member/register";
    }
    @PostMapping ("/member/register")
    public String register(MemberDTO dto){
        memberService.save(dto);
        return "/member/register";
    }
    @GetMapping("/member/registerSeller")
    public String registerSeller(){
        log.info("registerSeller...1");



        return "/member/registerSeller";
    }
    @GetMapping("/member/signup")
    public String signup(@RequestParam(name = "type") String type, Model model){
        log.info("signup...1");

        model.addAttribute("type",type);
        return "/member/signup";
    }
}
