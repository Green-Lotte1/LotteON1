package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.product.CartService;
import co.kr.lotteon.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService prodService;
    private final MainService mainService;
    private final CartService cartService;
    //////////////////////////////
    ////////    product aside 값 가져오기
    //////////////////////////////
    public void layout(Model model) {
        List<ProdCate1DTO> cate1 = prodService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = prodService.selectAllProdCate1AndProdCate2();
        mainService.appVersion(model);
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
        PageResponseDTO pageResponseDTO = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        model.addAttribute("products", pageResponseDTO);
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
        ProductDTO product =  prodService.selectProductByProdNo(pageRequestDTO.getProdNo());
        PageResponseDTO reviews = prodService.selectReviewByProdNo(pageRequestDTO);
        log.info(reviews.toString());
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "/product/view";
    }




    //////////////////////////////
    ////////    product cart
    //////////////////////////////
    @GetMapping(value = "/product/cart")
    public String cart(Model model ,PageRequestDTO pageRequestDTO) {
        layout(model);


        return "/product/cart";
    }

    @ResponseBody
    @GetMapping(value = "/product/cartCountProduct")
    public int cartCountProduct(PageRequestDTO pageRequestDTO) {
        log.info("cartCountProduct here...1");
        String uid = prodService.loginStatus();

        log.info("cartCountProduct uid: "+uid);

        int prodNo = pageRequestDTO.getProdNo();
        log.info("cartCountProduct prodNo: "+prodNo);
        log.info("cartCountProduct input: "+pageRequestDTO.getInput());
        // SELECT COUNT 처리
        int result = 0;
        log.info("cartCountProduct here...2");
        result = cartService.selectCountCartByUidAndProdNo(uid, prodNo);
        log.info("cartCountProduct result: "+result);
        log.info("cartCountProduct here...3");

        return result;
    }



    @ResponseBody
    @PostMapping(value = "/product/insertCartProduct")
    public int insertCartProduct(@RequestBody PageRequestDTO pageRequestDTO){

        log.info("insertCart here...1");

        String uid = prodService.loginStatus();
        log.info("insertCart here...check1");

        int prodNo = pageRequestDTO.getProdNo();
        log.info("insertCart here...check2");
        int input = pageRequestDTO.getInput();

        log.info("insertCart uid: "+uid);
        log.info("insertCart prodNo: "+prodNo);
        log.info("insertCart input: "+input);

        Map<String, Integer> map = new HashMap<String, Integer>();

        int result = pageRequestDTO.getResult();

        log.info("insertCart result: "+result);

        if(result > 0){
            log.info("insertCart here...2");
            // 상품이 장바구니에 있는 경우

            // UPDATE 처리
            /*if(jsonData.get("updateConfirm").equals("yes")){

            }else if(jsonData.get("updateConfirm").equals("no")){

            }*/
            cartService.updateCart(input, uid, prodNo);
            log.info("insertCart here...3");
            map.put("result", result);
            log.info("result 전송 성공...1");
            return result;
        }else if(result < 1){
            log.info("insertCart here...4");
            // 상품이 장바구니에 없는 경우

            // INSERT 처리
            cartService.insertCart(uid, prodNo, input);
            log.info("insertCart here...5");
            map.put("result", result);
            log.info("result 전송 성공...2");
            return result;
        }


        /*// 신규 등록일 경우
        if((Integer)jsonData.get("cartResult") == null){

            map.put("result", result);
            log.info("result 전송 성공");
            return map;

        // 이미 상품이 장바구니에 담겨있을 경우
        }else if(!((Integer)jsonData.get("cartResult") == null)){

        }

        if(result == 0){
            ProductDTO product = prodService.selectProductByProdNo(prodNo);
            cartService.insertCart(product);
        }*/
        log.info("insertCart here...6");

        return result;
    }





    @ResponseBody
    @PutMapping(value = "/product/cart")
    public void insertCart(){

    }

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
