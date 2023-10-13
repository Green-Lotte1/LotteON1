package co.kr.lotteon.controller.cs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String faqList(Model model) {
        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(Model model) {
        return "/cs/faq/view";
    }



    /////////////////////////////////////////////
    // 문의사항
    /////////////////////////////////////////////
    @GetMapping("/cs/qna/list")
    public String qnaList(Model model) {
        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/view")
    public String qnaView(Model model) {
        return "/cs/qna/view";
    }

    public String qnaWrite(Model model) {
        return "/cs/qna/write";
    }



    /////////////////////////////////////////////
    // 공지사항
    /////////////////////////////////////////////
    @GetMapping("/cs/notice/list")
    public String noticeList(Model model) {
        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String noticeView(Model model) {
        return "/cs/notice/view";
    }
}