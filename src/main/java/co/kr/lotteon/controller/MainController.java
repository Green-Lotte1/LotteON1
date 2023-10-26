package co.kr.lotteon.controller;

import co.kr.lotteon.dto.product.PageRequestDTO;
import co.kr.lotteon.dto.product.PageResponseDTO;
import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final ProductService prodService;
    private final MainService mainService;

    @GetMapping(value = {"/","/index"})
    public String index(Model model, PageRequestDTO pageRequestDTO){
        List<ProdCate1DTO> cate1 = prodService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = prodService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        pageRequestDTO.setSize(8);
        pageRequestDTO.setProdCate1(1);
        pageRequestDTO.setProdCate2(1);

        pageRequestDTO.setType("hit");
        PageResponseDTO hit = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        model.addAttribute("hit", hit.getDtoList());

        pageRequestDTO.setType("recommend");
        PageResponseDTO recommend = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        model.addAttribute("recommend", recommend.getDtoList());

        pageRequestDTO.setType("latest");
        PageResponseDTO latest = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        model.addAttribute("latest", latest.getDtoList());

        pageRequestDTO.setType("discount");
        PageResponseDTO discount = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        model.addAttribute("discount", discount.getDtoList());

        pageRequestDTO.setSize(5);
        pageRequestDTO.setType("hot");
        PageResponseDTO hot = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        model.addAttribute("hot", hot.getDtoList());



        mainService.appVersion(model);

        return "/index";
    }
}
