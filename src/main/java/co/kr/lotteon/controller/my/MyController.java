package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.cs.PageResponseDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.cs.CsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class MyController {

    @Autowired
    private CsService csService;

    @GetMapping("/my/coupon")
    public String coupon(){

        return "/my/coupon";
    }
    @GetMapping( value = {"/my/home", "/my"})
    public String home(){

        return "/my/home";
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
    @GetMapping("/my/order")
    public String order(){

        return "/my/order";
    }
    @GetMapping("/my/point")
    public String point(){

        return "/my/point";
    }
    @GetMapping("/my/qna")
    public String qna(Model model, PageRequestDTO pageRequestDTO){
        PageResponseDTO page = csService.myQna(pageRequestDTO);
        log.info("output result : ", page.getCsList());
        model.addAttribute("myQna", page);

        /*th:if="${list.comment > 0}"*/
        /* class="answerRow"의 display:none 상태를 풀면 답변을 볼 수 있음.*/

        return "/my/qna";
    }
    @GetMapping("/my/review")
    public String review(){

        return "/my/review";
    }
}
