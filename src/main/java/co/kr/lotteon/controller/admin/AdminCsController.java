package co.kr.lotteon.controller.admin;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class AdminCsController {

    ////////////////////////////////////////////////////////////////////
    // 공지사항 구현
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/admin/cs/notice/list")
    public String acnList() {
        return "/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/notice/view")
    public String acnView() {
        return "/admin/cs/view";
    }

    @GetMapping("/admin/cs/notice/write")
    public String acnWrite() {
        return "/admin/cs/notice/write";
    }

    @GetMapping("/admin/cs/notice/modify")
    public String acnModify() {
        return "/admin/cs/notice/modify";
    }



    ////////////////////////////////////////////////////////////////////
    // 자주묻는질문 구현
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/admin/cs/faq/list")
    public String acfList() {
        return "/admin/cs/faq/list";
    }

    @GetMapping("/admin/cs/faq/view")
    public String acfView() {
        return "/admin/cs/faq/view";
    }

    @GetMapping("/admin/cs/faq/write")
    public String acfWrite() {
        return "/admin/cs/faq/write";
    }

    @GetMapping("/admin/cs/faq/modify")
    public String acfModify() {
        return "/admin/cs/faq/modify";
    }



    ////////////////////////////////////////////////////////////////////
    // 문의사항 구현
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/admin/cs/qna/list")
    public String acqList() {
        return "/admin/cs/notice/list";
    }

    @GetMapping("/admin/cs/qna/view")
    public String acqView() {
        return "/admin/cs/qna/view";
    }

    @GetMapping("/admin/cs/qna/reply")
    public String acqReply() {
        return "/admin/cs/qna/reply";
    }
}