package co.kr.lotteon.controller.cs;

import co.kr.lotteon.dto.cs.*;
import co.kr.lotteon.service.CsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
public class CsController {

    @Autowired
    private CsService csService;

    /////////////////////////////////////////////
    // 인덱스
    /////////////////////////////////////////////
    @GetMapping(value = {"/cs", "/cs/index"})
    public String index(Model model, PageRequestDTO pageRequestDTO) {

        return "/cs/index";
    }



    /////////////////////////////////////////////
    // 자주 묻는 질문
    /////////////////////////////////////////////
    @GetMapping("/cs/faq/list")
    public String faqList(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);
        boardList(model, pageRequestDTO);

        return "/cs/faq/list";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);

        return "/cs/faq/view";
    }



    /////////////////////////////////////////////
    // 문의사항
    /////////////////////////////////////////////
    @GetMapping("/cs/qna/list")
    public String qnaList(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);
        boardList(model, pageRequestDTO);

        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/view")
    public String qnaView(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);

        return "/cs/qna/view";
    }

    @GetMapping("/cs/qna/write")
    public String qnaWrite(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);

        return "/cs/qna/write";
    }



    /////////////////////////////////////////////
    // 공지사항
    /////////////////////////////////////////////
    @GetMapping("/cs/notice/list")
    public String noticeList(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);
        boardList(model, pageRequestDTO);

        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/view")
    public String noticeView(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);

        return "/cs/notice/view";
    }






    //////////////////////////////////////////////////////////////////////////////////////////
    // Layout method (레이아웃 카테고리 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public void layout(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        String[] group_type = request.getRequestURI().split("/");

        // cate1 default값 동적처리
        pageRequestDTO.setGroup(group_type[3]);
        if(pageRequestDTO.getCate1().equals("101") && group_type[3].equals("faq")) {
            pageRequestDTO.setCate1("201");
        }else if(pageRequestDTO.getCate1().equals("101") && group_type[3].equals("qna")) {
            pageRequestDTO.setCate1("301");
        }

        log.info("@get.group : " + pageRequestDTO.getGroup());
        log.info("@get.cate1 : " + pageRequestDTO.getCate1());
        log.info("@get.cate2 : " + pageRequestDTO.getCate2());
        log.info("@get.article : " + group_type[4]);

        CsGroupDTO groupInfo = csService.groupInfo(pageRequestDTO.getGroup());
        log.info("groupInfo : " + groupInfo);
        CsCate1DTO cate1Info = csService.cate1Info(pageRequestDTO);
        log.info("cate1Info : " + cate1Info);
        List<CsCate1DTO> cate1List = csService.cate1List(pageRequestDTO);
        log.info("cate1List : " + cate1List);

        // aside atag에 담을 url
        String url = "/cs/"+group_type[3]+"/list";
        log.info("url : " + url);

        model.addAttribute("url", url);
        model.addAttribute("groupInfo", groupInfo);
        model.addAttribute("cate1Info", cate1Info);

        // model.addAttribute("cate2", group_type[2]);
        model.addAttribute("article", group_type[4]);
        model.addAttribute("category", cate1List);

        log.info("cate1............... : " + cate1Info.getCate1());
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // BoardList method (게시글 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public void boardList(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO boardList = csService.findCsLists(pageRequestDTO);
        if (pageRequestDTO.getGroup().equals("faq")) {
            log.info("boardList(faq) : " + boardList.getCsLists());
            model.addAttribute("boardList", boardList.getCsLists());
        }else {
            if (pageRequestDTO.getGroup().equals("qna")) {
                log.info("boardList(qna) : " + boardList.getCsList());
            }else {
                log.info("boardList(notice) : " + boardList.getCsList());
            }
            model.addAttribute("boardList", boardList.getCsList());
        }
    }
}