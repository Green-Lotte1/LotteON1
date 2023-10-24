package co.kr.lotteon.controller.policy;

import co.kr.lotteon.dto.member.TermsDTO;
import co.kr.lotteon.mapper.MemberMapper;
import co.kr.lotteon.service.policy.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PolicyController {

    @Autowired
    private  PolicyService policyService;

    @GetMapping("/policy/buyer")
    public String buyer (Model model) {
        List<TermsDTO> termsDTOList = policyService.getTermsList();
        model.addAttribute("selectpolicy",termsDTOList);

        return "/policy/buyer";
    }

    @GetMapping("/policy/privacy")
    public String privacy () {

        return "/policy/privacy";
    }

    @GetMapping("/policy/location")
    public String location () {

        return "/policy/location";
    }

    @GetMapping("/policy/finance")
    public String finance () {

        return "/policy/finance";
    }

    @GetMapping("/policy/seller")
    public String seller () {

        return "/policy/seller";
    }
}
