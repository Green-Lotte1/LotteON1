package co.kr.lotteon.service.admin;

import co.kr.lotteon.dto.admin.cs.PageRequestDTO;
import co.kr.lotteon.dto.admin.cs.PageResponseDTO;
import co.kr.lotteon.dto.cs.CsDTO;
import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import co.kr.lotteon.repository.cs.CsCate1Repository;
import co.kr.lotteon.repository.cs.CsCate2Repository;
import co.kr.lotteon.repository.cs.CsGroupRepository;
import co.kr.lotteon.repository.cs.CsRepository;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.service.cs.CsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Log4j2
@Builder
@RequiredArgsConstructor
@Service
public class AdminCsService {

    private final ModelMapper modelMapper;
    private final CsService csService;
    private final CsRepository csRepository;
    private final CsGroupRepository groupRepository;
    private final CsCate1Repository cate1Repository;
    private final CsCate2Repository cate2Repository;
    private final MemberRepository memberRepository;

    //////////////////////////////////////////////////////////////
    // admin/cs/*/list page
    //////////////////////////////////////////////////////////////
    public PageResponseDTO adminCsList(PageRequestDTO pageRequestDTO) {
        log.info(" ===== adminCsList() start ===== ");

        Pageable pageable = pageRequestDTO.getPageable("no");

        CsGroupEntity group = (pageRequestDTO.getGroup()!=null)?
                groupRepository.findById(pageRequestDTO.getGroup()).orElse(null):null;
        CsCate1Entity cate1 = (pageRequestDTO.getCate1()!=null)?
                cate1Repository.findById(pageRequestDTO.getCate1()).orElse(null):null;
        CsCate2Entity cate2 =  (pageRequestDTO.getCate2()!=null)?
                cate2Repository.findById(pageRequestDTO.getCate2()).orElse(null):null;

        log.info(" - 1. group : " + (group==null?"null":group.getGroup())
                + " / cate1 : " + (cate1==null?"null":cate1.getCate1())
                + " / cate2 : " + (cate2==null?"null":cate2.getCate2()));

        Page<CsEntity> entities = null;

        if(group != null) {
            // LIST(where group) - 전체 글 보기
            if (cate1 == null & cate2 == null) {
                entities = csRepository
                        .findByGroupAndParentLessThanEqual(group, 0, pageable);
                log.info(" - 2. adminCsList :: group");

                // LIST(where cate1) - 1차유형 글 보기
            } else if (cate2 == null) {
                entities = csRepository
                        .findByCate1AndParentLessThanEqual(cate1, 0, pageable);
                log.info(" - 2. adminCsList :: cate1");

                // LIST(where cate2) - 2차유형 글 보기
            } else {
                entities = csRepository
                        .findByCate2AndParentLessThanEqual(cate2, 0, pageable);
                log.info(" - 2. adminCsList :: cate2");
            }

            List<CsDTO> dtoList = csService.convertToCs(entities);
            log.info(" - 3. dtoList is " + (dtoList!=null?"OK":"NULL"));

            int totalElement = (int) entities.getTotalElements();
            log.info(" - 4. totalElement : " + totalElement);

            return PageResponseDTO.builder()
                    .pageRequestDTO(pageRequestDTO)
                    .csList(dtoList)
                    .total(totalElement)
                    .build();
        }
        log.info(" - 2. adminCsList is NULL");
        return null;
    }


    //////////////////////////////////////////////////////////////
    // admin/cs/*/view page
    //////////////////////////////////////////////////////////////
    public CsDTO adminCsView(Model model, PageRequestDTO pageRequestDTO) {
        log.info(" ===== adminCsView() start ===== ");

        int no = pageRequestDTO.getNo();
        log.info(" - 1. no : " + no);

        if(no != 0) {
            CsEntity entity = csRepository.findById(no).orElse(null);
            log.info(" - 2. entity is " + (entity!=null?"OK":"NULL..1"));

            CsDTO dto = entity.toDTO();
            int parent = dto.getParent();
            log.info(" - 3. parent : " + parent);

            if(parent > 0) {
                log.info(" - 4. `parent` is greater than 0.");
                CsEntity entity1 = csRepository.findByParent(parent);
                log.info(" -  answer is " + (entity1!=null?"OK":"NULL"));

                if(entity1!=null) {
                    log.info(" - 5. answer -> model");
                    model.addAttribute("answer",
                            csRepository.findByParent(parent).toDTO());
                }
            }

            model.addAttribute("cate1param", "cate1=" + dto.getCate1().getCate1() + "&");
            model.addAttribute("cate2param",
                    dto.getCate2().getCate2().equals("notice")?
                    "":"cate2=" + dto.getCate2().getCate2() + "&");

            log.info(" ----- -----  VIEW INFO  ---- ----- ");
            log.info(" - no      : " + dto.getNo());
            log.info(" - title   : " + dto.getTitle());
            log.info(" - content : " + dto.getContent());
            log.info(" - group   : " + dto.getGroup().getGroup());
            log.info("           : " + dto.getGroup().getGroup_name());
            log.info(" - cate1   : " + dto.getCate1().getCate1());
            log.info("           : " + dto.getCate1().getCate1_name());
            log.info(" - cate2   : " + dto.getCate2().getCate2());
            log.info("           : " + dto.getCate2().getCate2_name());
            log.info(" ----- ----- ----- ----- ---- ----- ");

            return dto;

        }else {
            log.info(" - 2. entity is NULL..2");
            return null;
        }
    }


    //////////////////////////////////////////////////////////////
    // admin/cs/*/write page
    //////////////////////////////////////////////////////////////
    public CsDTO adminCsWrite() {
        return null;
    }


    //////////////////////////////////////////////////////////////
    // admin/cs/*/modify page
    //////////////////////////////////////////////////////////////
    public CsDTO adminCsModify() {
        return null;
    }


    //////////////////////////////////////////////////////////////
    // admin/cs/qna/reply page
    //////////////////////////////////////////////////////////////
    public CsDTO adminCsReply() {
        return null;
    }


    //////////////////////////////////////////////////////////////
    // 카테고리 선택 메서드
    //////////////////////////////////////////////////////////////
    public void adminCsCate() {}


    //////////////////////////////////////////////////////////////
    // 추가 메서드
    //////////////////////////////////////////////////////////////
    // ctxPath 구하는 메서드
    public String ctxPath(HttpServletRequest request) {
        log.info(" ===== ctxPath() start ===== ");
        String ctxPath = request.getContextPath();
        log.info(" - ctxPath : " + ctxPath);

        return ctxPath;
    }

    // nav에서 group name 출력.
    public String nav(String group){
        log.info(" ===== nav() start ===== ");
        CsGroupEntity entity = groupRepository.findById(group).orElse(null);
        if(entity != null) {
            String groupName = entity.toDTO().getGroup_name();
            log.info(" - groupName : " + groupName);
            return groupName;
        }else {
            log.info(" - groupName is NULL");
            return null;
        }
    }

    // css 적용을 위해 section에 id값 주는 메서드
    public String forAdminCssCSS(Model model, HttpServletRequest request) {
        log.info(" ===== forAdminCssCSS() start ===== ");

        String curl = request.getRequestURI();
        log.info(" - 1. 현재주소 : " + curl);

        String[] arr = curl.split("/");
        log.info(" - 2. url 정보 = group : " + arr[4] + ", type : " + arr[5]);

        model.addAttribute("group", arr[4]);
        model.addAttribute("type",  arr[5]);

        return arr[4]; // group
        /* crul  = /LotteOn/admin/cs/notice/list
           group = {notice, qna, faq};
           type  = {list, view, modify, write, };  */
    }
}