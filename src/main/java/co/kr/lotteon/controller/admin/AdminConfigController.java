package co.kr.lotteon.controller.admin;

import co.kr.lotteon.service.admin.AdminService;
import co.kr.lotteon.service.CsService;
import co.kr.lotteon.service.MemberService;
import co.kr.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminConfigController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CsService csService;
    private final MemberService memberService;


    @GetMapping("/admin/config/banner")
    public String banner(){
        return "/admin/config/banner";
    }
    @GetMapping("/admin/config/info")
    public String info(){
        return "/admin/config/info";
    }





}



