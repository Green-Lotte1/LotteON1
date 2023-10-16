package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.product.PageRequestDTO;
import co.kr.lotteon.dto.product.PageResponseDTO;
import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
public class ProductController {

    @Autowired
    private ProductService prodService;

    public void layout(Model model) {
        List<ProdCate1DTO> cate1 = prodService.selectAllProdCate1();

        model.addAttribute("cate1List", cate1);
    }

    @GetMapping(value = "/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO){
        layout(model);

        PageResponseDTO pageResponseDTO = prodService.selectProductByCate1AndCate2(pageRequestDTO);

        model.addAttribute("products", pageRequestDTO);

        for(ProductDTO product : pageResponseDTO.getDtoList()){
            log.info(product.getProdNo());
            log.info(product.getProdName());
        }

        return "/product/list";
    }

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
