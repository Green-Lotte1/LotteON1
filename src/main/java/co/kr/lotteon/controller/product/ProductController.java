package co.kr.lotteon.controller.product;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.product.CartService;
import co.kr.lotteon.service.product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService prodService;
    private final MainService mainService;
    private final CartService cartService;
    private final MemberService memberService;
    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT ASIDE
    ////////////////////////////////////////////////////////////////////

    // PRODUCT LAYOUT ASIDE
    public void layout(Model model, HttpServletRequest request) {
        List<ProdCate1DTO> cate1 = prodService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = prodService.selectAllProdCate1AndProdCate2();
        String path = prodService.getPath(model, request);
        mainService.appVersion(model);
        model.addAttribute("path", path);
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);
    }




    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT NAV
    ////////////////////////////////////////////////////////////////////

    // PRODUCT NAV
    public void nav(Model model,PageRequestDTO pageRequestDTO){
        int cate1 = pageRequestDTO.getProdCate1();
        int cate2 = pageRequestDTO.getProdCate2();
        ProdCate2DTO cate = prodService.selectAllProdCateByCate2(cate1, cate2);

        model.addAttribute("cate", cate);
    }




    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT LIST
    ////////////////////////////////////////////////////////////////////

    // PRODUCT LIST
    @GetMapping(value = "/product/list")
    public String list(Model model, PageRequestDTO pageRequestDTO, HttpServletRequest request){
        layout(model, request);
        if(!(pageRequestDTO.getProdCate1() == 1)){
            nav(model, pageRequestDTO);
        }else{
            model.addAttribute("cate", null);
        }
        PageResponseDTO pageResponseDTO = prodService.selectProductByCate1AndCate2(pageRequestDTO);
        log.info("products: "+pageResponseDTO);
        model.addAttribute("products", pageResponseDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        log.info("here...4");
        return "/product/list";
    }




    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT VIEW
    ////////////////////////////////////////////////////////////////////

    // PRODUCT VIEW
    @GetMapping(value = "/product/view")
    public String view(Model model, PageRequestDTO pageRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        log.info("view here...1");
        layout(model, request);
        if(!(pageRequestDTO.getProdCate1() == 1)){
            nav(model, pageRequestDTO);
        }else{
            model.addAttribute("cate", null);
        }
        log.info("view here...2");
        ProductDTO product =  prodService.selectProductByProdNo(pageRequestDTO.getProdNo());
        log.info("view here...3");
        // productHit 유효성 검사 및 쿠키 생성
        viewCountValidation(product, request, response);
        log.info("view here...4");
        // 주소값으로 잘못된 주소 요청시 다른 페이지를 요청하게끔 구현 필요
        PageResponseDTO reviews = prodService.selectReviewByProdNo(pageRequestDTO);
        log.info("view here...5");
        log.info(reviews.toString());
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        return "/product/view";
    }

    // PRODUCT VIEW VALIDATION CHECK
    // 한달에 한번만 조회수 1 증가하게끔 하는 쿠키 생성 및 유효성 검사
    private void viewCountValidation(ProductDTO product, HttpServletRequest request, HttpServletResponse response) {
        int prodNo = product.getProdNo();
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElseGet(() ->new Cookie[0]);
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("productView"))
                .findFirst()
                .orElseGet(() -> {
                    prodService.addHitCount(prodNo);
                    return new Cookie("productView", "[" + prodNo + "]");
                });

        if (!cookie.getValue().contains("[" + prodNo + "]")) {
            prodService.addHitCount(prodNo);
            cookie.setValue(cookie.getValue() + "[" + prodNo + "]");
        }
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 한 달 후의 시간을 계산
        LocalDateTime oneMonthLater = now.plusMonths(1);

        // 초 단위로 변환
        long currentSecond = now.toEpochSecond(ZoneOffset.UTC);
        long oneMonthLaterSecond = oneMonthLater.toEpochSecond(ZoneOffset.UTC);

        /*long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
        long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);*/
        cookie.setPath("/"); // 모든 경로에서 접근 가능
        /*cookie.setMaxAge((int) (todayEndSecond - currentSecond)); // 오늘 하루 자정까지 남은 시간초 설정*/
        cookie.setMaxAge((int) (oneMonthLaterSecond - currentSecond)); // 한 달 동안의 초로 설정
        response.addCookie(cookie);
    }




    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT CART
    ////////////////////////////////////////////////////////////////////

    // PRODUCT CART
    @GetMapping(value = "/product/cart")
    public String cart(Model model ,PageRequestDTO pageRequestDTO, HttpServletRequest request) {
        layout(model, request);
        String uid = prodService.loginStatus();
        MemberDTO member = memberService.selectMemberByUid(uid);
        log.info("cart here...1");
        List<CartDTO> cartList = cartService.selectAllCartByUid(member);
        log.info("cart here...2");
        model.addAttribute("cartList", cartList);

        return "/product/cart";
    }

    // PRODUCT CART CHECK
    @ResponseBody
    @GetMapping(value = "/product/cartCountProduct")
    public int cartCountProduct(Model model, PageRequestDTO pageRequestDTO) {
        int result = 0;

        log.info("cartCountProduct here...1");
        String uid = prodService.loginStatus();

        log.info("cartCountProduct uid: "+uid);

        int prodNo = pageRequestDTO.getProdNo();
        log.info("cartCountProduct prodNo: "+prodNo);
        log.info("cartCountProduct input: "+pageRequestDTO.getInput());
        // SELECT COUNT 처리
        /*int result = 0;*/
        log.info("cartCountProduct here...2");
        result = cartService.selectCountCartByUidAndProdNo(model, uid, prodNo);
        log.info("cartCountProduct result: "+result);
        log.info("cartCountProduct here...3");

        return result;
    }


    // INSERT CART
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

        /*CHECK THIS PRODUCT IN CART RESULT
        result == 1 (THIS PRODUCT ALREADY IN CART)
        result == 0 (THIS PRODUCT NOT IN CART)*/
        int result = pageRequestDTO.getResult();

        log.info("insertCart result: "+result);

        // THIS PRODUCT ALREADY IN CART
        if(result > 0){
            log.info("insertCart here...2");
            // 상품이 장바구니에 있는 경우

            // UPDATE 처리
            result = cartService.updateCart(input, uid, prodNo);
            log.info("insertCart here...3");
            log.info("result 전송 성공...1");
            return result;
        // THIS PRODUCT NOT IN CART
        }else if(result < 1){
            log.info("insertCart here...4");
            // 상품이 장바구니에 없는 경우

            // INSERT 처리
            result = cartService.insertCart(uid, prodNo, input);
            log.info("insertCart here...5");
            log.info("result 전송 성공...2");
            return result;
        }
        log.info("insertCart here...6");

        return result;
    }


    // DELETE CART
    @ResponseBody
    @PostMapping(value = "/product/deleteCartProduct")
    public int deleteCartProductByProdNo(@RequestBody PageRequestDTO pageRequestDTO){

        int result = 0;

        result = cartService.deleteCartProductByCartNo(pageRequestDTO);

        return result;

    }


    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT ORDER
    ////////////////////////////////////////////////////////////////////

    // PRODUCT ORDER
    @GetMapping(value = "/product/order")
    public String order(Model model, HttpServletRequest request, @RequestParam("chk") String chk, PageRequestDTO pageRequestDTO) throws JsonProcessingException {
        layout(model, request);

        String uid = prodService.loginStatus();

        MemberDTO memberOrderInfo = memberService.selectMemberOrderInfoByUid(uid);
        log.info("getOrderController here...1");
        log.info("memberOrderInfo: "+memberOrderInfo.toString());
        List<ItemDTO> itemDTOList = prodService.selectProductForOrder(chk, uid);
        log.info("getOrderController here...2");
        log.info("itemDTOList: "+itemDTOList.toString());
        OrderDTO total = prodService.setTotalOrder(model, itemDTOList);
        log.info("getOrderController here...3");
        log.info("total: "+total.toString());

        model.addAttribute("total", total);
        model.addAttribute("memberOrderInfo", memberOrderInfo);
        model.addAttribute("itemDTOList", itemDTOList);

        return "/product/order";
    }




    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT COMPLETE
    ////////////////////////////////////////////////////////////////////

    // PRODUCT COMPLETE
    @GetMapping(value = "/product/complete")
    public String complete(Model model, HttpServletRequest request, PageRequestDTO pageRequestDTO) {
        layout(model, request);

        log.info(pageRequestDTO.getOrdNo());


        OrderDTO order = prodService.selectOrder(pageRequestDTO.getOrdNo());
        List<ItemDTO> itemDTOS = prodService.selectOrderItems(order.getOrdNo());

        log.info("complete here...1 :"+order.toString());
        log.info("complete here...2 :"+itemDTOS.toString());

        model.addAttribute("order", order);
        model.addAttribute("itemDTOS", itemDTOS);

        return "/product/complete";
    }

    @ResponseBody
    @PostMapping(value = "/product/insertOrder")
    public int insertOrder(Model model, HttpServletRequest request, @RequestBody OrderDTO order) {
        layout(model, request);

        log.info("insertOrderForm here...1");
        /*log.info("orderItemDTOList: "+orderItemDTOList.toString());*/
        /*log.info("insertOrderForm orderDTO: "+pageRequestDTO.getOrderDTO().toString());*/

/*        OrderDTO order = pageRequestDTO.getOrderDTO();*/

        log.info("insertOrderForm here...2");
        int result = 0;
        int ordNo = 0;

        log.info(order.toString());
        String uid = prodService.loginStatus();

        log.info("insertOrderController here...1");
        result = prodService.insertOrder(order);
        ordNo = prodService.selectLatestOrdNo();
        prodService.minusMemberPoint(order.getUsedPoint());

        log.info("insertOrderController here...2");
        log.info("ordNo: "+ordNo);
        return ordNo;
    }

    @ResponseBody
    @PostMapping(value = "/product/insertOrderItems")
    public int insertOrderItems(
                                @RequestParam("jsonData")String jsonData,
                                @RequestParam("ordNo") int ordNo) throws JsonProcessingException{
    int result = 0;

    log.info("insertOrderItems here...1");
    ObjectMapper objectMapper = new ObjectMapper();
    List<ItemDTO> itemDTOS = objectMapper.readValue(jsonData, new TypeReference<List<ItemDTO>>() {
    });

    log.info(itemDTOS.toString());

    log.info("insertOrderItems here...2");
    prodService.insertOrderItems(itemDTOS, ordNo);
    log.info("insertOrderItems here...3");
    return result;
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////// PRODUCT SEARCH
    ////////////////////////////////////////////////////////////////////

    @GetMapping(value = "/product/search")
    public String search(Model model,
                         HttpServletRequest request,
                         PageRequestDTO pageRequestDTO) {
        log.info("search here...1");
        layout(model, request);
        log.info("search here...2");
        /*String type = */
        // 페이징 처리 영역 *************************************************
        int total = 0;
        int pg = pageRequestDTO.getPg();
        // 현재 페이지 번호
        int currentPage = prodService.getCurrentPage(pg);
        total = prodService.selectSearchCountProducts(pageRequestDTO.getKeyword(), pageRequestDTO.getProdCate1());

        // 마지막 페이지 번호
        int lastPageNum = prodService.getLastPageNum(total);

        // 페이지 그룹 start, end 번호
        int[] result = prodService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작번호
        int pageStartNum = prodService.getPageStartNum(total, currentPage);

        // 시작 인덱스
        int start = prodService.getStartNum(currentPage);

        List<ProductDTO> products = prodService.searchProducts(pageRequestDTO, start);
        model.addAttribute("total", total);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageGroupStart", result[0]);
        model.addAttribute("pageGroupEnd", result[1]);
        model.addAttribute("pageStartNum", pageStartNum+1);
        model.addAttribute("products", products);
        /*model.addAttribute("pageRequestDTO", pageRequestDTO);*/

        return "/product/search";
    }

    /*@ResponseBody
    @PostMapping(value = "/product/searchDetail")
    public String searchDetail(@RequestParam("jsonData")String jsonData) throws JsonProcessingException{


        ObjectMapper objectMapper = new ObjectMapper();
        SearchDetailDTO searchDetailDTO = objectMapper.readValue(jsonData, new TypeReference<SearchDetailDTO>() {
        });

        log.info("searchDetail here...1");
        log.info("searchDetail jsonData: "+searchDetailDTO);

        return null;
    }*/
}
