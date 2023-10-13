package co.kr.lotteon.controller.cs;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Log4j2
@Controller
public class CsController {

    /////////////////////////////////////////////
    // 인덱스
    /////////////////////////////////////////////
    @GetMapping(value = {"/cs", "/cs/index"})
    public String index() {
        return "/cs/index";
    }



    /////////////////////////////////////////////
    // 자주 묻는 질문
    /////////////////////////////////////////////

    @GetMapping("/cs/faq/list")
    public String faqList(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/faq/view";
    }



    /////////////////////////////////////////////
    // 문의사항
    /////////////////////////////////////////////
    @GetMapping("/cs/qna/list")
    public String qnaList(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/view")
    public String qnaView(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/qna/view";
    }

    @GetMapping("/cs/qna/write")
    public String qnaWrite(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/qna/write";
    }



    /////////////////////////////////////////////
    // 공지사항
    /////////////////////////////////////////////
    @GetMapping("/cs/notice/list")
    public String noticeList(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String noticeView(HttpServletRequest request, Model model) {
        String[] cate_type = request.getRequestURI().split("/");
        log.info("testdebug_cate : " + cate_type[2]);
        log.info("testdebug_type : " + cate_type[3]);

        model.addAttribute("cate_type", cate_type);
        return "/cs/notice/view";
    }
}