package co.kr.lotteon.controller.admin;

import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.service.admin.AdminProductService;
import co.kr.lotteon.service.admin.AdminService;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.File;
import java.util.List;

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

        List<AdminProductDTO> productDTOList = adminProductService.selectProducts();
        model.addAttribute("adminproducts" , productDTOList );
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String productRegister(){
        String path = new File(filePath).getAbsolutePath();
        log.info("path : " + path);
        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String productRegister(AdminProductDTO productDTO, HttpServletRequest request){

        log.info("register...1 : " + productDTO);
        String ip = request.getRemoteAddr();
        productDTO.setIp(ip);

        adminProductService.insertProduct(productDTO);


        return "redirect:/admin/product/register";
    }

    @GetMapping("/admin/product/delete")
    public String deleteProduct(int prodNo){
        log.info("deleteProduct...1");
        adminProductService.deleteProduct(prodNo);

        return "redirect:/admin/product/list";
    }
}



