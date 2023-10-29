package co.kr.lotteon.controller.admin;

import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.repository.product.ProductRepository;
import co.kr.lotteon.service.admin.AdminProductService;
import co.kr.lotteon.service.admin.AdminService;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final AdminService adminService;
    private final ProductService productService;
    private final CsService csService;
    private final MemberService memberService;
    private final AdminProductService adminProductService;
    private final ModelMapper modelMapper;

    @Value("${spring.servlet.multipart.location}")
    private String filePath;


    /////////// list paging
   /* @GetMapping("/admin/product/list")
    public String productList(Model model, PageRequestDTO productPageRequestDTO){

        List<AdminProductDTO> productDTOList = adminProductService.selectProducts();
        model.addAttribute("adminproducts" , productDTOList );
        return "/admin/product/list";
    }*/

    @GetMapping("/admin/product/modify")
    public String productModify(Model model, PageRequestDTO pageRequestDTO, HttpServletRequest request) {
        model.addAttribute("ctxPath", request.getContextPath());
        ProductDTO view = productService.selectProductByProdNo(pageRequestDTO.getProdNo());
        model.addAttribute("view", view);

        int cate1 = view.getProdCate1().getCate1();
        int cate2 = view.getProdCate2();
        model.addAttribute("cate1", cate1);
        model.addAttribute("cate2", cate2);

        pageRequestDTO.setProdCate1(cate1);
        pageRequestDTO.setProdCate2(cate2);
        List<ProdCate1DTO> cate1List = productService.selectAllProdCate1();
        List<ProdCate2DTO> cate2List = productService.selectProdCate2(cate1);
        model.addAttribute("cate1List", cate1List);
        model.addAttribute("cate2List", cate2List);

        log.info("file path location : " + filePath);
        model.addAttribute("filePath", filePath);
        /*Path filePath = Paths.get();*/


        return "/admin/product/modify";
    }


    @PostMapping("/admin/product/modify")
    public String productModify(AdminProductDTO adminProductDTO) {
        log.info("here 1... prodNo : " + adminProductDTO.getProdNo());
        ProductDTO   dto   = productService.selectProductByProdNo(adminProductDTO.getProdNo());
        log.info("here 2");
        ProdCate1DTO cate1 = adminProductService.selectCate1(adminProductDTO.getProdCate1());
        log.info("here 3");
        int          cate2 = adminProductDTO.getProdCate2();
        log.info("here 4");

        dto.setProdCate1(cate1);
        dto.setProdCate2(cate2);
        dto.setProdName(adminProductDTO.getProdName());
        dto.setDescript(adminProductDTO.getDescript());
        dto.setProdCompany(adminProductDTO.getProdCompany());
        dto.setPrice(adminProductDTO.getPrice());
        dto.setDiscount(adminProductDTO.getDiscount());
        dto.setPoint(adminProductDTO.getPoint());
        dto.setStock(adminProductDTO.getStock());
        dto.setDelivery(adminProductDTO.getDelivery());
        dto.setStatus(adminProductDTO.getStatus());
        dto.setDuty(adminProductDTO.getDuty());
        dto.setReceipt(adminProductDTO.getReceipt());
        dto.setBizType(adminProductDTO.getBizType());
        dto.setOrigin(adminProductDTO.getOrigin());

        log.info("thumb1 : " + adminProductDTO.getFileThumb1().getOriginalFilename());
        log.info("thumb2 : " + adminProductDTO.getFileThumb2().getOriginalFilename());
        log.info("thumb3 : " + adminProductDTO.getFileThumb3().getOriginalFilename());
        log.info("detail : " + adminProductDTO.getFileDetail().getOriginalFilename());

        List<MultipartFile> files = Arrays.asList(
                adminProductDTO.getFileThumb1(),
                adminProductDTO.getFileThumb2(),
                adminProductDTO.getFileThumb3(),
                adminProductDTO.getFileDetail()
        );

        List<String> saveNames = adminProductService.fileUploadByModify(adminProductDTO, files);
        if(!saveNames.get(0).equals("")) dto.setThumb1(saveNames.get(0));
        if(!saveNames.get(1).equals("")) dto.setThumb2(saveNames.get(1));
        if(!saveNames.get(2).equals("")) dto.setThumb3(saveNames.get(2));
        if(!saveNames.get(3).equals("")) dto.setDetail(saveNames.get(3));
        log.info("thumb1 : " + dto.getThumb1());
        log.info("thumb2 : " + dto.getThumb2());
        log.info("thumb3 : " + dto.getThumb3());
        log.info("detail : " + dto.getDetail());
        log.info("dto    : " + dto.toString());

        adminProductService.updateProduct1(dto);


        log.info("dto : " + dto);

        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String productRegister(HttpServletRequest request, Model model){
        String path = new File(filePath).getAbsolutePath();
        log.info("path : " + path);
        String ctxPath = productService.getPath(model, request);
        model.addAttribute("ctxPath", ctxPath);
        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String productRegister(AdminProductDTO productDTO, HttpServletRequest request){

        log.info("register...1 : " + productDTO);
        String ip = request.getRemoteAddr();
        productDTO.setIp(ip);
        productDTO.setSale(1);
        double realPoint = productDTO.getPoint();
        int point = (int) Math.round(realPoint);
        productDTO.setPoint(point);

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

    @ResponseBody
    @PostMapping("/admin/product/deleteCheckProduct")
    public List<String> deleteCheckProduct(@RequestBody Map<String,List<String>> data){
        log.info("deleteCheckProduct...1");
        List<String> prodNos = data.get("prodNo");

        for(String prodNo : prodNos){
            adminProductService.deleteProduct(Integer.parseInt(prodNo));
        }

        return prodNos;
    }

    // 페이징

    @GetMapping("/admin/product/list")
    public String list(@RequestParam(name = "search", required = false) String search,
                       @RequestParam(name = "searchType", required = false) String searchType,
                       Model model,
                       String pg) {

        log.info("컨트롤러 1=====================================");
        int total = 0;
        if(search == null && searchType == null) {
            total = adminProductService.selectProductCountTotal();
        } else {
            total = adminProductService.searchProductsCount(search,searchType);
        }

        log.info("컨트롤러 토탈========================"+total);

        int lastPageNum = adminProductService.getLastPageNum(total);

        log.info("컨트롤러 토탈========================"+lastPageNum);

        int currentPg = adminProductService.getCurrentPage(pg);

        log.info("컨트롤러 토탈========================"+currentPg);

        int[] pageGroup =  adminProductService.getPageGroupNum(currentPg, lastPageNum);

        log.info("페이지 그룹======================="+ Arrays.toString(pageGroup));

        int start = adminProductService.getStartNum(currentPg);


        log.info("컨트롤러 토탈========================"+start);


        // 상품 목록 출력
        List<AdminProductDTO> products = null;

        if(search == null && searchType == null){
            products = adminProductService.selectPageProducts(start);

        } else {
            products = adminProductService.searchProduct(search,searchType,start);
        }

        // 뷰(템플릿)에서 참조하기 위해 모델 참조
        model.addAttribute("products", products);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("total ", total);
        model.addAttribute("currentPg", currentPg);
        model.addAttribute("pageGroupStart", pageGroup[0]);
        model.addAttribute("pageGroupEnd", pageGroup[1]);


        return "/admin/product/list";
    }

}



