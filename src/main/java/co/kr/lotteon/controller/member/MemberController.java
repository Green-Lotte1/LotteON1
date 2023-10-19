package co.kr.lotteon.controller.member;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.member.TermsDTO;
import co.kr.lotteon.service.member.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    /*public MemberDTO test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        MemberDTO member = memberService.findByUid(username);
        return member;
    }*/

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
    public String register(Model model){
        log.info("register...1");
        model.addAttribute("member",new MemberDTO());
        return "/member/register";
    }
    @PostMapping ("/member/register")
    public String register(@ModelAttribute MemberDTO member, HttpServletRequest request) throws ServletException {
        log.info("register...2");
        member.setRegip(request.getRemoteAddr());
        member.setLevel(1);
        member.setType(1);

        memberService.insert(member);
        return "redirect:/member/login";
    }
    @GetMapping("/member/registerSeller")
    public String registerSeller(Model model){
        log.info("registerSeller...1");
        model.addAttribute("member",new MemberDTO());
        return "/member/registerSeller";
    }
    @PostMapping("/member/registerSeller")
    public String registerSeller(@ModelAttribute MemberDTO member, HttpServletRequest request){
        log.info("registerSeller...2");
        member.setRegip(request.getRemoteAddr());
        member.setLevel(5);
        member.setType(2);
        member.setName(member.getManager());
        member.setHp(member.getManagerHp());

        memberService.insert(member);

        return "redirect:/member/login";
    }
    @GetMapping("/member/signup")
    public String signup(@RequestParam(name = "type") String type, Model model){
        log.info("signup...1");

        TermsDTO dto = memberService.findTerms();
        model.addAttribute("sign",dto);
        model.addAttribute("type",type);
        return "/member/signup";
    }
}
