package co.kr.lotteon.controller;

import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.product.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Log4j2
public class MainController {

    @Autowired
    private ProductService prodService;
    @Autowired
    private MainService mainService;

    @GetMapping(value = {"/","/index"})
    public String index(Model model){
        List<ProdCate1DTO> cate1 = prodService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = prodService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        mainService.appVersion(model);

        return "/index";
    }
}
