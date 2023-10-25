package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.service.cs.CsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String info(){

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
        model.addAttribute("myQna", csService.myQna(pageRequestDTO));

        /* class="answerRow"의 display:none 상태를 풀면 답변을 볼 수 있음.*/

        return "/my/qna";
    }
    @GetMapping("/my/review")
    public String review(){

        return "/my/review";
    }
}
