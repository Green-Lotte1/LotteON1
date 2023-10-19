package co.kr.lotteon.controller.admin;

import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.service.admin.AdminProductService;
import co.kr.lotteon.service.admin.AdminService;
import co.kr.lotteon.service.CsService;
import co.kr.lotteon.service.MemberService;
import co.kr.lotteon.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CsService csService;
    private final MemberService memberService;
    private final AdminProductService adminProductService;

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    @GetMapping("/admin/product/list")
    public String productList(Model model, PageRequestDTO pageRequestDTO){
       // PageResponseDTO pageResponseDTO = productServic
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String productRegister(){
        String path = new File(filePath).getAbsolutePath();
        log.info("path : " + path);
        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String productRegister(ProductDTO productDTO, HttpServletRequest request){

        log.info("register...1 : " + productDTO);
        String ip = request.getRemoteAddr();
        productDTO.setIp(ip);

        adminProductService.insertProduct(productDTO);


        return "redirect:/admin/product/register";
    }

}



