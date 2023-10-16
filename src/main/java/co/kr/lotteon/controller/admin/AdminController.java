package co.kr.lotteon.controller.admin;

import co.kr.lotteon.service.AdminService;
import co.kr.lotteon.service.CsService;
import co.kr.lotteon.service.MemberService;
import co.kr.lotteon.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CsService csService;
    @Autowired
    private MemberService memberService;

    @GetMapping(value = {"/admin","/admin/index"})
    public String index(){
        return "/admin/index";
    }
    @GetMapping("/admin/config/banner")
    public String banner(){
        return "/admin/config/banner";
    }
    @GetMapping("/admin/config/info")
    public String info(){
        return "/admin/config/info";
    }
    @GetMapping("/admin/product/list")
    public String productList(){
        return "/admin/product/list";
    }
    @GetMapping("/admin/product/register")
    public String productReg(){
        return "/admin/product/register";
    }



}



