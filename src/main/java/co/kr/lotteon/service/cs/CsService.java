package co.kr.lotteon.service.cs;

import co.kr.lotteon.dto.cs.*;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.cs.CsCate1Repository;
import co.kr.lotteon.repository.cs.CsCate2Repository;
import co.kr.lotteon.repository.cs.CsGroupRepository;
import co.kr.lotteon.repository.cs.CsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Builder
@RequiredArgsConstructor
@Service
public class CsService {

    private final ModelMapper modelMapper;
    private final CsRepository csRepository;
    private final CsGroupRepository groupRepository;
    private final CsCate1Repository cate1Repository;
    private final CsCate2Repository cate2Repository;
    private final MemberRepository memberRepository;

    //////////////////////////////////
    // DTO <-> Entity Convert
    //////////////////////////////////
    public List<CsDTO> convertToCs(List<CsEntity> entities) {
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CsDTO.class))
                .collect(Collectors.toList());
    }

    public List<CsDTO> convertToCs(Page<CsEntity> entities) {
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CsDTO.class))
                .collect(Collectors.toList());
    }

    public List<CsCate1DTO> convertToCate1(List<CsCate1Entity> entities) {
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CsCate1DTO.class))
                .collect(Collectors.toList());
    }

    public List<CsCate2DTO> convertToCate2(List<CsCate2Entity> entities) {
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CsCate2DTO.class))
                .collect(Collectors.toList());
    }


    ////////////////////////////////////////////////////////////////////
    // Layout 구현
    ////////////////////////////////////////////////////////////////////
    // aside영역 cate1 List 출력
    public List<CsCate1DTO> cate1List(PageRequestDTO pageRequestDTO) {
        CsGroupEntity group = groupRepository.findById(pageRequestDTO.getGroup()).orElse(null);
        log.info("cate1List group : " + group);
        List<CsCate1Entity> entity = null;
        if (group != null) {
            entity = cate1Repository.findByGroup(group);
        }
        log.info("cate1List entity : " + entity);
        List<CsCate1DTO> dto = null;
        if (entity != null) {
            dto = convertToCate1(entity);
        }
        log.info("cate1List dto : " + dto);
        return dto;
    }

    // group info 출력
    public CsGroupDTO groupInfo(String group) {
        CsGroupEntity entity = groupRepository.findById(group).orElse(null);
        log.info("groupInfo entity : " + entity);
        CsGroupDTO dto = null;
        if (entity != null) {
            dto = entity.toDTO();
        }
        log.info("groupInfo dto : " + dto);
        return dto;
    }


    // cate1 Info 출력
    public CsCate1DTO cate1Info(PageRequestDTO pageRequestDTO) {
        String cate1 = pageRequestDTO.getCate1();
        log.info("cate1Info cate1 : " + cate1);
        CsCate1Entity entity = cate1Repository.findById(cate1).orElse(null);
        log.info("cate1Info Method : " + entity);
        CsCate1DTO dto = null;
        if (entity != null) {
            dto = entity.toDTO();
        }
        log.info("cate1Info dto : " + dto);
        return dto;
    }

    ////////////////////////////////////////////////////////////////////
    // index page
    ////////////////////////////////////////////////////////////////////
    // index page qna, notice list 출력
    public PageResponseDTO indexList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");

        List<CsDTO> dto = null;
        int totalElement = 0;

        // index 페이지에서 pg=2를 하는거 막기 위함.
        pageRequestDTO.setPg(1);

        CsGroupEntity group = groupRepository.findById(pageRequestDTO.getGroup()).orElse(null);
        log.info("indexList group : " + group);

        Page<CsEntity> entities = csRepository.findByGroupAndParentLessThanEqual(group,0, pageable);
        log.info("indexList entities : " + entities);

        if(entities != null) {
            log.info("indexList entities IS NOT NULL");
            dto = convertToCs(entities);
            totalElement = (int) entities.getTotalElements();
        }else {
            log.info("indexList entities IS NULL");
        }
        log.info("indexList dto : " + dto);
        log.info("indexList totalElement : " + totalElement);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .csList(dto)
                .total(totalElement)
                .build();
    }

    // faq cate1 출력
    public List<CsCate1DTO> faqCate1List() {
        CsGroupEntity group = groupRepository.findById("faq").orElse(null);
        List<CsCate1Entity> entity = cate1Repository.findByGroup(group);
        List<CsCate1DTO> dto = convertToCate1(entity);

        return dto;
    }


    ////////////////////////////////////////////////////////////////////
    // list page
    ////////////////////////////////////////////////////////////////////
    // 리스트 출력하기
    public PageResponseDTO findCsLists(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");

        // 초기화
        Page<CsEntity> page = null;
        List<List<CsDTO>> faq = new ArrayList<>();
        int totalElement = 0;

        // 그룹, 카테고리 확인
        String group = pageRequestDTO.getGroup();
        log.info("findCsLists group : " + group);
        String cate1 = pageRequestDTO.getCate1();
        log.info("findCsLists cate1 : " + cate1);
        int cate2 = Integer.parseInt(pageRequestDTO.getCate2());
        log.info("findCsLists cate2 : " + cate2);

        // group entity
        CsGroupEntity csGroupEntity = groupRepository.findById(group).orElse(null);
        log.info("findCsLists csGroupEntity : " + csGroupEntity);

        // cate1 entity
        CsCate1Entity cate1Entity = cate1Repository.findById(cate1).orElse(null);
        log.info("findCsLists cate1Entity : " + cate1Entity);

        // 게시글 출력 시작
        if(group.equals("notice") && cate1.equals("101")) {
            // notice 전체 출력
            log.info("findCsLists (notice_all)...");
            page = csRepository.findByGroupAndParentLessThanEqual(csGroupEntity,0, pageable);

        }else if(group.equals("faq")) {
            // faq 출력 (cate2마다 출력.)
            log.info("findCsLists (faq)...");

            List<CsCate2Entity> findByCate1 = cate2Repository.findByCate1(cate1Entity);
            log.info("findCsLists findByCate1 : " + findByCate1);

            for(CsCate2Entity ent : findByCate1) {
                Page<CsEntity> pagaFaq = csRepository.findByGroupAndCate1AndCate2AndParentLessThanEqual(csGroupEntity, cate1Entity, ent, 0, pageable);
                List<CsDTO> listFaq = convertToCs(pagaFaq);
                faq.add(listFaq);
            }
            log.info("findCsLists : " + faq);

            return PageResponseDTO.builder()
                    .pageRequestDTO(pageRequestDTO)
                    .csLists(faq)
                    .total(totalElement)
                    .build();

        }else {
            // 나머지 cate1을 기준으로 list 출력
            log.info("findCsLists (default)...");
            page = csRepository.findByCate1AndParentLessThanEqual(cate1Entity, 0, pageable);
        }
        log.info("findCsLists page : " + page);

        totalElement = (int) page.getTotalElements();
        log.info("findCsLists totalElement : " + totalElement);

        List<CsDTO> dto = page.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, CsDTO.class))
                .toList();
        log.info("findCsLists dto : " + dto);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .csList(dto)
                .total(totalElement)
                .build();
    }



    ////////////////////////////////////////////////////////////////////
    // view page
    ////////////////////////////////////////////////////////////////////
    // 게시글 출력하기
    public CsDTO findById(int no) {
        CsEntity entity = csRepository.findById(no).orElse(null);
        CsDTO dto = entity.toDTO();
        return dto;
    }

    // 답변 출련하기
    public CsDTO findByParent(int parent) {
        CsEntity entity = csRepository.findByParent(parent);
        CsDTO dto = entity.toDTO();
        return dto;
    }


    // 게시글 삭제하기
    public String deleteBoard(int no, String cate1) {
        // uid, sec.username 비교
        String ownUid = findById(no).getUid().getUid();
        String userna = SecurityContextHolder.getContext().getAuthentication().getName();
        String result = "";

        log.info("ownUid : " + ownUid);
        log.info("userna : " + userna);

        if (ownUid.equals(userna)) {
            csRepository.deleteById(no);

            int cnt = csRepository.countByNo(no);
            log.info("cnt : " + cnt);

            if(cnt == 0) {
                // 게시글 삭제 성공
                log.info("DELETE SUCCESS");
                return "success";

            }else {
                // 게시글 삭제 실패
                log.info("DELETE FAILED");
                return "/cs/qna/view?cate1=" + cate1 + "&no=" + no + "&success=101";
            }
        }
        // 권한이 없습니다.
        log.info("DELETE NO PERMISSION");
        return "/cs/qna/view?cate1=" + cate1 + "&no=" + no + "&success=403";
    }



    ////////////////////////////////////////////////////////////////////
    // write page
    ////////////////////////////////////////////////////////////////////
    // cate1_name 출력용
    public List<CsCate1DTO> findByCate(String group) {
        log.info(" ===== findByCate() start ===== ");
        CsGroupEntity entity = groupRepository.findById(group).orElse(null);
        List<CsCate1Entity> entities = cate1Repository.findByGroup(entity);
        List<CsCate1DTO> dto = convertToCate1(entities);

        // group의 경우 cate1에 '전체'가 포함되어있으므로 제외함.
        if(group.equals("notice")) {
            dto.remove(0);
        }

        for(int i=0; i<dto.size(); i++) {
            log.info(" - " + (i+1) + ". " + dto.get(i).getCate1_name());
        }

        return dto;
    }

    // cate2_name 출력용
    public List<CsCate2DTO> findByCate1(String cate1) {
        log.info(" ===== findByCate1() start ===== ");
        List<CsCate2DTO> dto = null;
        if(cate1 != null) {
            CsCate1Entity entity = cate1Repository.findById(cate1).orElse(null);
            List<CsCate2Entity> entities = cate2Repository.findByCate1(entity);
            dto = convertToCate2(entities);
            for(int i=0; i<dto.size(); i++) {
                log.info(" - " + (i+1) + ". " + dto.get(i).getCate2_name());
            }
        }
        return dto;
    }

    // 게시글 작성
    @Transactional
    public int saveBoard(PageRequestDTO pageRequestDTO) {
        log.info("csService Start!");

        pageRequestDTO.setUid(loginStatus());
        log.info("uid : " + loginStatus());

        CsGroupEntity groupEntity  = groupRepository.findById(pageRequestDTO.getGroup()).orElse(null);
        CsCate1Entity cate1Entity  = cate1Repository.findById(pageRequestDTO.getCate1()).orElse(null);
        CsCate2Entity cate2Entity  = cate2Repository.findById(pageRequestDTO.getCate2()).orElse(null);
        MemberEntity  memberEntity = memberRepository.findById(pageRequestDTO.getUid()).orElse(null);
        log.info("no : " + pageRequestDTO.getNo());

        log.info("groupEntity  : " + groupEntity);
        log.info("cate1Entity  : " + cate1Entity);
        log.info("cate2Entity  : " + cate2Entity);
        log.info("memberEntity : " + memberEntity);

        CsEntity entity = new CsEntity();
        entity.setGroup(groupEntity);
        entity.setCate1(cate1Entity);
        entity.setCate2(cate2Entity);
        entity.setUid(memberEntity);
        entity.setTitle(pageRequestDTO.getTitle());
        entity.setContent(pageRequestDTO.getContent());

        log.info("entity : " + entity);

        if(pageRequestDTO.getNo() != 0) {
            // 게시글을 수정할 때
            log.info("csService...a");
            CsDTO dto = findById(pageRequestDTO.getNo());
            String uid = dto.getUid().getUid();
            String use = loginStatus();
            log.info("uid : " + uid + "/ use : " + use);

            if(!uid.equals(use)) {
                log.info("csService...b");
                // 권한 없음
                return 403;

            }else {
                log.info("csService...c");
                // 게시글 수정
                entity.setNo(dto.getNo());
                entity.setParent(dto.getParent());
                entity.setRdate(dto.getRdate());
            }
        }
        CsEntity result = csRepository.save(entity);
        log.info("result : " + result);
        log.info("csService...END");
        return (result != null)? 0:100;
    }

    // 게시글 수정


    //////////////////////////////////////////////////////////////////////////////////////////
    // View method (게시글 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public CsDTO view(int no, Model model) {
        CsDTO dto = findById(no);
        log.info("view : " + dto);
        model.addAttribute("view", dto);
        return dto;
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    // Layout method (레이아웃 카테고리 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public void layout(HttpServletRequest request, Model model, PageRequestDTO pageRequestDTO) {
        loginStatus();
        String[] group_type = request.getRequestURI().split("/");
        // cate1 default값 동적처리
        pageRequestDTO.setGroup(group_type[3]);
        if(!(pageRequestDTO.getGroup().equals("notice")) && pageRequestDTO.getCate1().equals("101")) {
            String cate1 = group_type[3].equals("faq") ? "201" : "301";
            pageRequestDTO.setCate1(cate1);
        }

        log.info("@get.no    : " + pageRequestDTO.getNo());
        log.info("@get.group : " + pageRequestDTO.getGroup());
        log.info("@get.cate1 : " + pageRequestDTO.getCate1());
        log.info("@get.cate2 : " + pageRequestDTO.getCate2());
        log.info("@get.article : " + group_type[4]);
        log.info("success : " + pageRequestDTO.getSuccess());

        CsGroupDTO groupInfo = groupInfo(pageRequestDTO.getGroup());
        log.info("groupInfo : " + groupInfo);
        CsCate1DTO cate1Info = cate1Info(pageRequestDTO);
        log.info("cate1Info : " + cate1Info);
        List<CsCate1DTO> cate1List = cate1List(pageRequestDTO);
        log.info("cate1List : " + cate1List);

        // path
        String path = request.getContextPath();
        model.addAttribute("path", path);
        log.info("path : " + path);

        // aside atag에 담을 url
        String url = "/cs/"+group_type[3]+"/list";
        log.info("url : " + url);

        model.addAttribute("success", pageRequestDTO.getSuccess());

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

        PageResponseDTO boardList = findCsLists(pageRequestDTO);

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



    //////////////////////////////////////////////////////////////////////////////////////////
    // login status method (로그인 정보 출력 메서드)
    //////////////////////////////////////////////////////////////////////////////////////////
    public String loginStatus() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("username : " + SecurityContextHolder.getContext().getAuthentication().getName());

        return username;
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = ((UserDetails) principal).getUsername();
        log.info("username : " + username);*/
    }
}
