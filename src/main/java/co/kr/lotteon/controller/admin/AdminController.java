package co.kr.lotteon.controller.admin;


import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.service.admin.AdminService;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CsService csService;
    private final MemberService memberService;


    @GetMapping(value = {"/admin","/admin/index"})
    public String index(){
        return "/admin/index";
    }


}



