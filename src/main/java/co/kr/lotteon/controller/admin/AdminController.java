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

    @GetMapping(value = "/product/view")
    public String view() {
        return "/product/view";
    }
    @GetMapping(value = "/product/cart")
    public String cart() {
        return "/product/cart";
    }
    @GetMapping(value = "/product/order")
    public String order() {
        return "/product/order";
    }
    @GetMapping(value = "/product/complete")
    public String complete() {
        return "/product/complete";
    }



}



