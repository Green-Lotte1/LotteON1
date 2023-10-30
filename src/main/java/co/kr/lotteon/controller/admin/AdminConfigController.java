package co.kr.lotteon.controller.admin;

import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.admin.AdminService;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminConfigController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CsService csService;
    private final MemberService memberService;
    private final MainService mainService;


    @GetMapping("/admin/config/banner")
    public String banner(Model model){
        mainService.appVersion(model);
        return "/admin/config/banner";
    }
    @GetMapping("/admin/config/info")
    public String info(Model model){
        mainService.appVersion(model);
        return "/admin/config/info";
    }





}



