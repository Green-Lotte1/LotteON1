package co.kr.lotteon.controller.policy;

import co.kr.lotteon.dto.policy.PolicyTermsDTO;
import co.kr.lotteon.dto.product.PageRequestDTO;
import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;

import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.policy.PolicyService;
import co.kr.lotteon.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class PolicyController {

    private final PolicyService  policyService;
    private final ProductService productService;
    private final MainService    mainService;

    @GetMapping("/policy/buyer")
    public String buyer (Model model,PageRequestDTO pageRequestDTO) {

        List<ProdCate1DTO> cate1 = productService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = productService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        mainService.appVersion(model);

        List<PolicyTermsDTO> dto =  policyService.selectPolicyBuyer();
        model.addAttribute("selectPolicy",dto);

       /* List<PolicyTermsDTO> policyTermsDTOList = policyService.getPolicyTermsList();
        model.addAttribute("selectpolicy",policyTermsDTOList);
*/

        return "/policy/buyer";
    }

    @GetMapping("/policy/finance")
    public String privacy (Model model, PageRequestDTO pageRequestDTO) {

        List<ProdCate1DTO> cate1 = productService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = productService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        mainService.appVersion(model);

        List<PolicyTermsDTO> dto =  policyService.selectPolicyFinance();
        model.addAttribute("selectPolicy",dto);

        return "/policy/finance";
    }

    @GetMapping("/policy/location")
    public String location (Model model, PageRequestDTO pageRequestDTO) {

        List<ProdCate1DTO> cate1 = productService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = productService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        mainService.appVersion(model);

        List<PolicyTermsDTO> dto =  policyService.selectPolicyLocation();
        model.addAttribute("selectPolicy",dto);

        return "/policy/location";
    }

    @GetMapping("/policy/privacy")
    public String finance (Model model, PageRequestDTO pageRequestDTO) {

        List<ProdCate1DTO> cate1 = productService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = productService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        mainService.appVersion(model);

        List<PolicyTermsDTO> dto =  policyService.selectPolicyPrivacy();
        log.info(dto);
        model.addAttribute("selectPolicy",dto);

        return "/policy/privacy";
    }

    @GetMapping("/policy/seller")
    public String seller (Model model, PageRequestDTO pageRequestDTO) {
        List<ProdCate1DTO> cate1 = productService.selectAllProdCate1();
        List<ProdCate2DTO> cate2 = productService.selectAllProdCate1AndProdCate2();
        model.addAttribute("cate1List", cate1);
        model.addAttribute("cate2List", cate2);

        mainService.appVersion(model);

        List<PolicyTermsDTO> dto =  policyService.selectPolicySeller();
        log.info(dto);

        model.addAttribute("selectPolicy",dto);
        /*log.info("dto : " + dto);
        if(dto != null) {
            String[] termsss = dto.get(0).getTermsArr();
            for(int i=0; i<termsss.length; i++) {
                log.info("teemrs["+i+"]" + termsss[i]);
            }

        }
*/
        return "/policy/seller";
    }
}
