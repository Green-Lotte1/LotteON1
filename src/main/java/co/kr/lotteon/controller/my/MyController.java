package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.admin.cs.PageResponseDTO;
import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.my.PageRequestMyDTO;
import co.kr.lotteon.dto.my.PageResponseMyDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.security.MyUserDetails;
import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.coupon.CouponService;
import co.kr.lotteon.service.coupon.MemberCouponService;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import co.kr.lotteon.service.my.MyService;
import co.kr.lotteon.service.product.ReviewService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class MyController {

    private     final     MyService              myService;
    private     final     CsService              csService;
    private     final     MainService            mainService;
    private     final     ReviewService          reviewService;
    private     final     MemberService          memberService;
    private     final     MemberCouponService    memberCouponService;

    @GetMapping( value = {"/my/home", "/my"})
    public String home(Model model, PageRequestMyDTO pageRequestMyDTO){
        myService.myPageLayout(model);
        mainService.appVersion(model);

        String uid = memberService.MyAccount().getUid();

        co.kr.lotteon.dto.product.PageRequestDTO pageRequestDTO_Product =
                co.kr.lotteon.dto.product.PageRequestDTO.builder().build();
        PageRequestDTO pageRequestDTO_Cs = PageRequestDTO.builder().uid(uid).build();

        pageRequestMyDTO.setUid(uid);
        pageRequestMyDTO.setType1("");
        pageRequestMyDTO.setType2("");

        model.addAttribute("myOrder",  myService.myOrderList(pageRequestMyDTO));
        model.addAttribute("myPoint",  myService.myPointList(pageRequestMyDTO));
        model.addAttribute("myReview", reviewService.myReview(pageRequestDTO_Product));
        model.addAttribute("myQna",    csService.myQna(pageRequestDTO_Cs));


        return "/my/home";
    }
    @GetMapping("/my/coupon")
    public String coupon(Model model,
                         co.kr.lotteon.dto.admin.cs.PageRequestDTO pageRequestDTO){
        myService.myPageLayout(model);
        mainService.appVersion(model);
        PageResponseDTO pageResponseDTO = memberCouponService.myCouponList(pageRequestDTO);
        model.addAttribute("myCoupons", pageResponseDTO);
        model.addAttribute("currentMyCoupon", pageResponseDTO.getNo());
        model.addAttribute("status", pageRequestDTO.getStatus());

        return "/my/coupon";
    }
    @GetMapping("/my/info")
    public String info(Model model){
        myService.myPageLayout(model);
        mainService.appVersion(model);
        log.info("info...1");
        MemberDTO memberDTO = memberService.MyAccount();
        model.addAttribute("myMember",memberDTO);
            return "/my/info";
    }

    @ResponseBody
    @PutMapping("/my/update/user")
    public int updateMember(@RequestBody MemberDTO dto, @AuthenticationPrincipal MyUserDetails myUser){
        log.info("updateMember...1");
        int result = 0;

        MemberDTO user = memberService.updateMember(dto); // DB 변경
        myUser.setMember(user.toEntity()); // 로그인된 사용자 정보 변경
        if(user != null){
            result = 1;
        }
        return result;
    }

    @GetMapping("/my/order")
    public String order(Model model, HttpServletRequest request, PageRequestMyDTO pageRequestMyDTO){
        myService.myPageLayout(model);
        mainService.appVersion(model);

        pageRequestMyDTO.setUid(memberService.MyAccount().getUid());
        PageResponseMyDTO page = myService.myOrderList(pageRequestMyDTO);

        model.addAttribute("type1",   pageRequestMyDTO.getType1());
        model.addAttribute("type2",   pageRequestMyDTO.getType2());
        model.addAttribute("path",    request.getContextPath());
        model.addAttribute("myOrder", page);

        return "/my/order";
    }
    @GetMapping("/my/point")
    public String point(Model model, HttpServletRequest request , PageRequestMyDTO pageRequestMyDTO){
        myService.myPageLayout(model);
        mainService.appVersion(model);

        pageRequestMyDTO.setUid(memberService.MyAccount().getUid());
        PageResponseMyDTO page = myService.myPointList(pageRequestMyDTO);

        model.addAttribute("type1",   pageRequestMyDTO.getType1());
        model.addAttribute("type2",   pageRequestMyDTO.getType2());
        model.addAttribute("path",    request.getContextPath());
        model.addAttribute("myPoint", page);

        return "/my/point";
    }
    @GetMapping("/my/qna")
    public String qna(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO){
        myService.myPageLayout(model);
        mainService.appVersion(model);
        log.info("qna()...1");
        model.addAttribute("myQna", csService.myQna(pageRequestDTO));
        model.addAttribute("path",  request.getContextPath());

        log.info("qna()...2");
        return "/my/qna";
    }

    @ResponseBody
    @RequestMapping(value = "/my/myQna", method = RequestMethod.POST)
    public HashMap<String, Object> myQnaList(@RequestBody HashMap<String, Object> uid) {
        log.info("myQnaList()...1");
        log.info("no : " + uid.get("qno").toString());

        uid.put("answer", csService.findByParent(Integer.parseInt(uid.get("qno").toString())));

        log.info("myQnaList()...2");
        return uid;
    }

    @GetMapping("/my/review")
    public String review(HttpServletRequest request, Model model,
                         co.kr.lotteon.dto.product.PageRequestDTO pageRequestDTO){
        myService.myPageLayout(model);
        mainService.appVersion(model);
        log.info("review()...1");
        model.addAttribute("myReview", reviewService.myReview(pageRequestDTO));

        log.info("review()...2");
        return "/my/review";
    }

    @ResponseBody
    @PostMapping("/my/deleteMyAccount")
    public int deleteMyAccount(@RequestBody String uid,@AuthenticationPrincipal MyUserDetails myUser, HttpServletRequest request, HttpServletResponse response){
        log.info("deleteMyAccount...1");

        memberService.deleteMyAccount(uid);

        // 로그아웃 처리
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, authentication);

        return 0;
    }

    @GetMapping("/my/modifyPass")
    public String modifyPass(Model model){
        myService.myPageLayout(model);
        mainService.appVersion(model);
        return "/my/modifyPass";
    }
}
