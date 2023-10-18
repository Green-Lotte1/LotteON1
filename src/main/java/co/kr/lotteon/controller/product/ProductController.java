package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService prodService;

    //////////////////////////////
    ////////    product aside 값 가져오기
    //////////////////////////////
    public void layout(Model model) {
        List<ProdCate1DTO> cate1 = prodService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = prodService.selectAllProdCate1AndProdCate2();

        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);
    }
    //////////////////////////////
    ////////    product nav 값 가져오기
    //////////////////////////////
    public void nav(Model model,PageRequestDTO pageRequestDTO){
        int cate1 = pageRequestDTO.getProdCate1();
        int cate2 = pageRequestDTO.getProdCate2();
        ProdCate2DTO cate = prodService.selectAllProdCateByCate2(cate1, cate2);

        model.addAttribute("cate", cate);
    }

    //////////////////////////////
    ////////    product list
    //////////////////////////////
    @GetMapping(value = "/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO){
        layout(model);
        if(!(pageRequestDTO.getProdCate1() == 1)){
            nav(model, pageRequestDTO);
        }else{
            model.addAttribute("cate", null);
        }
        log.info("here...1");

        PageResponseDTO pageResponseDTO = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        log.info("here...2");

        model.addAttribute("products", pageResponseDTO);

        log.info("here...3");

        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        log.info("here...4");
        return "/product/list";
    }

    //////////////////////////////
    ////////    product view
    //////////////////////////////
    @GetMapping(value = "/product/view")
    public String view(Model model, PageRequestDTO pageRequestDTO) {
        layout(model);
        if(!(pageRequestDTO.getProdCate1() == 1)){
            nav(model, pageRequestDTO);
        }else{
            model.addAttribute("cate", null);
        }
        log.info("view here...1");
        ProductDTO product =  prodService.selectProductByProdNo(pageRequestDTO.getProdNo());
        log.info("view here...2");
        PageResponseDTO reviews = prodService.selectReviewByProdNo(pageRequestDTO);
        log.info(reviews.toString());
        log.info("productName :"+ product.getProdName());
        log.info("view here...3");
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "/product/view";
    }
    //////////////////////////////
    ////////    product cart
    //////////////////////////////
    @GetMapping(value = "/product/cart")
    public String cart(Model model) {
        layout(model);

        return "/product/cart";
    }
    /*@ResponseBody
    @PostMapping(value = "/product/cart")
    public void cart(MyUserDetails member, PageRequestDTO pageRequestDTO){
        String uid = member.getMember().getUid();
        int prodNo = pageRequestDTO.getProdNo();
        prodService.selectCountCartByUidAndProdNo(uid, prodNo);
    }
    @ResponseBody
    @PutMapping(value = "/product/cart")
    public void insertCart(){

    }*/
    //////////////////////////////
    ////////    product order
    //////////////////////////////
    @GetMapping(value = "/product/order")
    public String order(Model model) {
        layout(model);
        return "/product/order";
    }
    //////////////////////////////
    ////////    product complete
    //////////////////////////////
    @GetMapping(value = "/product/complete")
    public String complete(Model model) {
        layout(model);
        return "/product/complete";
    }
}
