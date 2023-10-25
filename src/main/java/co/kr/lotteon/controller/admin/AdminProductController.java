package co.kr.lotteon.controller.admin;

import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
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
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.Map;

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

    /*@GetMapping("/admin/product/list")
    public String productList(Model model, PageRequestDTO pageRequestDTO){

        List<AdminProductDTO> productDTOList = adminProductService.selectProducts();
        model.addAttribute("adminproducts" , productDTOList );
        return "/admin/product/list";
    }*/

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


        return "redirect:/admin/product/list";
    }

    @ResponseBody
    @PostMapping("/admin/product/register/category")
    public List<ProdCate2DTO> productRegisterCate(@RequestBody co.kr.lotteon.dto.product.PageRequestDTO pageRequestDTO){
        log.info("productRegisterCate...1");
        int cate1 = pageRequestDTO.getProdCate1();
        log.info(cate1);

        List<ProdCate2DTO> category = adminProductService.SelectProductCate(cate1);
        log.info(category.toString());

        return category;
    }

    @GetMapping("/admin/product/delete")
    public String deleteProduct(int prodNo){
        log.info("deleteProduct...1");
        adminProductService.deleteProduct(prodNo);

        return "redirect:/admin/product/list";
    }

    @PostMapping("/admin/product/searchProduct")
    public String SearchProduct(@RequestParam("search") String search, @RequestParam("searchType") String searchType, Model model){
        log.info("SearchProduct...1");
        List<AdminProductDTO> productDTOList = adminProductService.searchProduct(search, searchType);
        model.addAttribute("adminproducts" , productDTOList );

        return "/admin/product/list";
    }
    @ResponseBody
    @PostMapping("/admin/product/deleteCheckProduct")
    public List<String> deleteCheckProduct(@RequestBody Map<String,List<String>> data){
        log.info("deleteCheckProduct...1");
        List<String> prodNos = data.get("prodNo");

        for(String prodNo : prodNos){
            adminProductService.deleteProduct(Integer.parseInt(prodNo));
        }

        return prodNos;    }

// 페이징

    @GetMapping("/admin/product/list")
    public String list(Model model, String pg) {

        int total = AdminProductService.selectProductCountTotal();
        int lastPageNum = AdminProductService.getLastPageNum(total);
        int currentPg = AdminProductService.getCurrentPage(pg);
        int start = AdminProductService.getStartNum(currentPg);


        // 상품 목록 출력
        List<AdminProductDTO> products = AdminProductService.selectPageProducts(start);

        // 뷰(템플릿)에서 참조하기 위해 모델 참조
        model.addAttribute("products", products);
        model.addAttribute("lastPageNum", lastPageNum);


        return "/admin/product/list";
    }



}



