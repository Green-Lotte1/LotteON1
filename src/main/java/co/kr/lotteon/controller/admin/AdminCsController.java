package co.kr.lotteon.controller.admin;

import co.kr.lotteon.dto.admin.cs.PageRequestDTO;
import co.kr.lotteon.dto.admin.cs.PageResponseDTO;
import co.kr.lotteon.dto.cs.CsDTO;
import co.kr.lotteon.service.MainService;
import co.kr.lotteon.service.admin.AdminCsService;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@Controller
public class AdminCsController {

    private final MainService    mainService;
    private final AdminCsService adminCsService;
    private final CsService      csService;
    private final MemberService  memberService;

    ////////////////////////////////////////////////////////////////////
    // 공지사항 구현
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/admin/cs/notice/list")
    public String acnList(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        model.addAttribute("adminCsList",
                adminCsService.adminCsList(pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/list";
    }

    @GetMapping("/admin/cs/notice/view")
    public String acnView(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        // include model... : answer (CsDTO)
        model.addAttribute("view",
                adminCsService.adminCsView(model, pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/view";
    }

    @GetMapping("/admin/cs/notice/write")
    public String acnWrite(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/write";
    }

    @GetMapping("/admin/cs/notice/modify")
    public String acnModify(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        // include model... : answer (CsDTO)
        CsDTO modify = adminCsService.adminCsView(model, pageRequestDTO);
        model.addAttribute("view", modify);

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("cate2List",
                csService.findByCate1(modify.getCate1().getCate1()));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/modify";
    }



    ////////////////////////////////////////////////////////////////////
    // 자주묻는질문 구현
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/admin/cs/faq/list")
    public String acfList(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        model.addAttribute("adminCsList",
                adminCsService.adminCsList(pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("cate2List",
                csService.findByCate1(pageRequestDTO.getCate1()));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/list";
    }

    @GetMapping("/admin/cs/faq/view")
    public String acfView(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        // include model... : answer (CsDTO)
        model.addAttribute("view",
                adminCsService.adminCsView(model, pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/view";
    }

    @GetMapping("/admin/cs/faq/write")
    public String acfWrite(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("cate2List",
                csService.findByCate1(pageRequestDTO.getCate1()));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/write";
    }

    @GetMapping("/admin/cs/faq/modify")
    public String acfModify(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        // include model... : answer (CsDTO)
        CsDTO modify = adminCsService.adminCsView(model, pageRequestDTO);
        model.addAttribute("view", modify);

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("cate2List",
                csService.findByCate1(modify.getCate1().getCate1()));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/modify";
    }



    ////////////////////////////////////////////////////////////////////
    // 문의사항 구현
    ////////////////////////////////////////////////////////////////////
    @GetMapping("/admin/cs/qna/list")
    public String acqList(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        model.addAttribute("adminCsList",
                adminCsService.adminCsList(pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("cate1List",
                csService.findByCate(group));

        model.addAttribute("cate2List",
                csService.findByCate1(pageRequestDTO.getCate1()));

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/list";
    }

    @GetMapping("/admin/cs/qna/view")
    public String acqView(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        // include model... : answer (CsDTO)
        model.addAttribute("view",
                adminCsService.adminCsView(model, pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/view";
    }

    @GetMapping("/admin/cs/qna/reply")
    public String acqReply(HttpServletRequest request, PageRequestDTO pageRequestDTO, Model model) {
        // include model.. : appInfo (version info)
        mainService.appVersion(model);

        // include model... : String group (faq, qna..), type (list, view..), pg;
        String group = adminCsService.forAdminCssCSS(model, request, pageRequestDTO);
        pageRequestDTO.setGroup(group);

        model.addAttribute("groupName",
                adminCsService.nav(group));

        // include model... : answer (CsDTO)
        model.addAttribute("view",
                adminCsService.adminCsView(model, pageRequestDTO));

        model.addAttribute("selectedCate1", pageRequestDTO.getCate1());
        model.addAttribute("selectedCate2", pageRequestDTO.getCate2());

        model.addAttribute("path",
                adminCsService.ctxPath(request));

        return "/admin/cs/reply";
    }



    ////////////////////////////////////////////////////////////////////
    // 게시글 삭제
    ////////////////////////////////////////////////////////////////////
    @ResponseBody
    @RequestMapping(value = "/cs/deletes", method = RequestMethod.DELETE)
    public String csDelete(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info(" ===== csDelete :: DELETE START ===== ");
        // 접근 유저 정보
        String username  = csService.loginStatus();
        int level = memberService.selectRoleByUid(username);

        // 글쓴이 정보
        if(level != 7) {
            String writer = csService.findById(pageRequestDTO.getNo()).getUid().getUid();
            if(username.equals(writer)){
                return "403";
            }
        }
        log.info(" - delete validation ok");

        if(pageRequestDTO.getNoSelect().equals("/")) {
            // 숫자 삭제.
            int no = pageRequestDTO.getNo();
            log.info(" - no : " + no + " delete...");
            adminCsService.adminCsDelete(no);
            return "200";

        }else {
            // 숫자 리스트 삭제
            String comma = pageRequestDTO.getNoSelect().replace("/", ", ");
            log.info(" - no : " + comma + "delete...");
            String[] noarr = pageRequestDTO.getNoSelect().split("/"); // "/3/3/4/5/76/534/123/" 같은 구조.
            for(int i=1; i<noarr.length; i++) {
                int no = Integer.parseInt(noarr[i]);
                adminCsService.adminCsDelete(no);
            }
            return "200";
        }
    }



    ////////////////////////////////////////////////////////////////////
    // 게시글 작성 및 수정
    ////////////////////////////////////////////////////////////////////
    @PostMapping("/admin/cs/write")
    public String acWrite(PageRequestDTO pageRequestDTO) {
        pageRequestDTO.setUid(csService.loginStatus());
        int result = adminCsService.adminCsWrite(pageRequestDTO);

        // 작성한 게시글, 답글단 게시글로 return
        return "redirect:/admin/cs/" + pageRequestDTO.getGroup()
                + "/view?no=" + result;
    }


    ////////////////////////////////////////////////////////////////////
    // 게시글 목록 동적 처리
    ////////////////////////////////////////////////////////////////////
    @ResponseBody
    @RequestMapping(value = "/cs/list", method = RequestMethod.POST)
    public PageResponseDTO jsonList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info(" vvvvv jsonList START vvvvv ");
        PageResponseDTO result = adminCsService.adminCsList(pageRequestDTO);
        log.info(" - jl.total : " + result.getTotal());
        log.info(" - jl.total : " + result);
        log.info(" ^^^^^ jsonList END ^^^^^ ");
        return result;
    }

}




