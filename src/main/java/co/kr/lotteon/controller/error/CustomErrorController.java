package co.kr.lotteon.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    // 존재하지 않은 페이지
    @GetMapping("/error/404")
    public String error404(){
        
        return "/error/404";
    }

    // 권한(로그인)이 필요한 페이지
    @GetMapping("/error/403")
    public String error403(){

        return "/error/403";
    }
}
