package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.PageRequestDTO;
import co.kr.lotteon.dto.PageResponseDTO;
import co.kr.lotteon.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping(value = "/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO){

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
