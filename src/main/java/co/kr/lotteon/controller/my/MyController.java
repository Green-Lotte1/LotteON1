package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.cs.PageResponseDTO;
import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.product.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Log4j2
@Controller
public class MyController {

    @Autowired
    private CsService csService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private MemberService memberService;

    @GetMapping( value = {"/my/home", "/my"})
    public String home(){

        return "/my/home";
    }
    @GetMapping("/my/coupon")
    public String coupon(){

        return "/my/coupon";
    }
    @GetMapping("/my/info")
    public String info(Model model){
        log.info("info...1");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof MyUserDetails) {
                MyUserDetails myUserDetails = (MyUserDetails) userDetails;
                MemberEntity memberEntity = myUserDetails.getMember();

                model.addAttribute("myMember",memberEntity);
            }
        }

            return "/my/info";
    }

    @PutMapping("/my/update/user")
    public String updateMember(@RequestBody MemberDTO dto){
        log.info("updateMember...1");
        memberService.updateMember(dto);
        return null;
    }

    @GetMapping("/my/order")
    public String order(){

        return "/my/order";
    }
    @GetMapping("/my/point")
    public String point(){

        return "/my/point";
    }
    @GetMapping("/my/qna")
    public String qna(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO){
        log.info("qna()...1");
        model.addAttribute("myQna", csService.myQna(pageRequestDTO));
        model.addAttribute("path",  request.getContextPath());

        log.info("qna()...2");
        return "/my/qna";
    }

    @ResponseBody
    @RequestMapping(value = "/my/myQna", method = RequestMethod.POST)
    public HashMap<String, Object> myQnaList(@RequestBody HashMap<String, Object> uid) {
        log.info("myQnaList()...1");
        log.info("no : " + uid.get("qno").toString());

        uid.put("answer", csService.findByParent(Integer.parseInt(uid.get("qno").toString())));

        log.info("myQnaList()...2");
        return uid;
    }

    @GetMapping("/my/review")
    public String review(HttpServletRequest request, Model model,
                         co.kr.lotteon.dto.product.PageRequestDTO pageRequestDTO){
        log.info("review()...1");
        model.addAttribute("myReview", reviewService.myReview(pageRequestDTO));

        log.info("review()...2");
        return "/my/review";
    }
}
