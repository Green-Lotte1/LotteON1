package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.product.*;
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
        List<ProdCate2DTO> cate2 = prodService.selectAllProdCate1AndProdCate2();

        for(ProdCate2DTO cate : cate2){
            log.info(cate.toString());
        }

        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);
    }

    @GetMapping(value = "/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO){
        layout(model);

        log.info("here...1");

        PageResponseDTO pageResponseDTO = prodService.selectProductByCate1AndCate2(pageRequestDTO);

        log.info("here...2");

        model.addAttribute("products", pageResponseDTO);

        log.info("here...3");

        for(ProductDTO product : pageResponseDTO.getDtoList()){
            log.info(product.getProdNo());
            log.info(product.getProdName());
        }

        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        log.info("here...4");
        return "/product/list";
    }

    @GetMapping(value = "/product/view")
    public String view(Model model, int prodNo) {
        layout(model);
        /*ProductDTO product =  prodService.selectProductByProdNo(prodNo);

        model.addAttribute("product", product);*/
        return "/product/view";
    }
    @GetMapping(value = "/product/cart")
    public String cart(Model model) {
        layout(model);

        return "/product/cart";
    }
    @GetMapping(value = "/product/order")
    public String order(Model model) {
        layout(model);
        return "/product/order";
    }
    @GetMapping(value = "/product/complete")
    public String complete(Model model) {
        layout(model);
        return "/product/complete";
    }
}
