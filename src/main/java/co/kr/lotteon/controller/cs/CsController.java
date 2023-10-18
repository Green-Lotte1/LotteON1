package co.kr.lotteon.controller.cs;

import co.kr.lotteon.dto.cs.*;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import co.kr.lotteon.service.CsService;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public String index(Model model, PageRequestDTO pageRequestDTO, Pageable pageable) {
        List<CsCate1DTO> faqCate1List = csService.faqCate1List();
        log.info("faqCate1List : " + faqCate1List);

        // index에선 list를 5개씩만 출력하기 위함.
        pageRequestDTO.setSize(5);

        pageRequestDTO.setGroup("notice");
        PageResponseDTO notice = csService.indexList(pageRequestDTO);
        log.info("index notice list : " + notice);
        log.info("notice size() : " + notice.getCsList().size());

        pageRequestDTO.setGroup("qna");
        PageResponseDTO qna = csService.indexList(pageRequestDTO);
        log.info("index qna list : " + qna);
        log.info("qna size() : " + notice.getCsList().size());

        model.addAttribute("ntc", notice);
        model.addAttribute("cate1List", faqCate1List);
        model.addAttribute("qna", qna);

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
        if (pageRequestDTO.getNo() == 0) {
            // 비정상적 접근( no == 0 일 때,)
            return "redirect:/cs/faq/list";
        }
        view(pageRequestDTO.getNo(), model);

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
        if (pageRequestDTO.getNo() == 0) {
            // 비정상적 접근( no == 0 일 때,)
            return "redirect:/cs/qna/list";
        }
        view(pageRequestDTO.getNo(), model);

        return "/cs/qna/view";
    }

    // 문의사항 게시글 작성 폼
    @GetMapping("/cs/qna/write")
    public String qnaWrite(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        layout(request, model, pageRequestDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        log.info("group : " + pageRequestDTO.getGroup());
        log.info("cate1 : " + pageRequestDTO.getCate1());
        log.info("cate2 : " + pageRequestDTO.getCate2());
        log.info("pg : " + pageRequestDTO.getPg());

        // cate1_name 출력용 (경로... cate1.cate1_name)
        List<CsCate1DTO> cate1 = csService.findByCate(pageRequestDTO.getGroup());
        model.addAttribute("cate1", cate1);
        log.info("cate1 result : " + cate1);

        // cate2_name 출력용 (경로... cate2.cate2_name)
        List<CsCate2DTO> cate2 = csService.findByCate1(pageRequestDTO.getCate1());
        model.addAttribute("cate2", cate2);
        log.info("cate2 result : " + cate2);

        // json url을 위한 contextPath
        String path = request.getContextPath();
        String url = path + "/cs/qna/write";
        model.addAttribute("path", path);
        model.addAttribute("url", url);
        log.info("path : " + path);
        log.info("url : " + url);

        log.info(" ========================================= ");
        log.info("group   : " + pageRequestDTO.getGroup());
        log.info("cate1   : " + pageRequestDTO.getCate1());
        log.info("cate2   : " + pageRequestDTO.getCate2());
        log.info("uid     : " + pageRequestDTO.getUid());
        log.info("title   : " + pageRequestDTO.getTitle());
        log.info("content : " + pageRequestDTO.getContent());
        log.info(" ========================================= ");

        return "/cs/qna/write";
    }

    // 문의사항 게시글 작성 후 전송
    @PostMapping("/cs/qna/write")
    public String qnaWrite(PageRequestDTO pageRequestDTO) {

        log.info(" ========================================= ");
        log.info("group   : " + pageRequestDTO.getGroup());
        log.info("cate1   : " + pageRequestDTO.getCate1());
        log.info("cate2   : " + pageRequestDTO.getCate2());
        log.info("uid     : " + pageRequestDTO.getUid());
        log.info("title   : " + pageRequestDTO.getTitle());
        log.info("content : " + pageRequestDTO.getContent());
        log.info(" ========================================= ");

        int result = csService.insertQna(pageRequestDTO);
        log.info("result : " + result);
        log.info("게시글 등록에 " + (result==1?"성공":"실패") + "하였습니다.");

        return "redirect:/cs/qna/list?cate1=" + pageRequestDTO.getCate1();
    }

    // 문의사항 게시글 작성시, cate loading
    @ResponseBody
    @RequestMapping(value = "/cs/qna/list", method = RequestMethod.POST)
    public String cateJson(@RequestBody HashMap<String, Object>map) {
        Object selectCate1 = map.get("selectCate1");
        log.info("selectCate1 : " + selectCate1);

        return "";
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
        if (pageRequestDTO.getNo() == 0) {
            // 비정상적 접근( no == 0 일 때,)
            return "redirect:/cs/notice/list";
        }
        view(pageRequestDTO.getNo(), model);

        return "/cs/notice/view";
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    // View method (게시글 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public void view(int no, Model model) {
        CsDTO dto = csService.findById(no);
        log.info("view : " + dto);
        model.addAttribute("view", dto);
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    // Layout method (레이아웃 카테고리 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public void layout(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        String[] group_type = request.getRequestURI().split("/");
        // cate1 default값 동적처리
        pageRequestDTO.setGroup(group_type[3]);
        if(!(pageRequestDTO.getGroup().equals("notice")) && pageRequestDTO.getCate1().equals("101")) {
            String cate1 = group_type[3].equals("faq") ? "201" : "301";
            pageRequestDTO.setCate1(cate1);
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

        model.addAttribute("article", group_type[4]);
        model.addAttribute("category", cate1List);
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    // BoardList method (리스트 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public void boardList(Model model, PageRequestDTO pageRequestDTO) {
        log.info("size check : " + pageRequestDTO.getSize());

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
        log.info("boardList : " + boardList);

        log.info("testdebug_ pageResponseDTO pg    : " + boardList.getPg());
        log.info("testdebug_ pageResponseDTO size  : " + boardList.getSize());
        log.info("testdebug_ pageResponseDTO total : " + boardList.getTotal());
        log.info("testdebug_ pageResponseDTO start : " + boardList.getStart());
        log.info("testdebug_ pageResponseDTO end   : " + boardList.getEnd());
        log.info("testdebug_ pageResponseDTO prev  : " + boardList.isPrev());
        log.info("testdebug_ pageResponseDTO next  : " + boardList.isNext());

        model.addAttribute("pageResponseDTO", boardList);
    }
}