package co.kr.lotteon.service.admin;

import co.kr.lotteon.dto.admin.cs.PageRequestDTO;
import co.kr.lotteon.dto.admin.cs.PageResponseDTO;
import co.kr.lotteon.dto.cs.CsDTO;
import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.repository.cs.CsCate1Repository;
import co.kr.lotteon.repository.cs.CsCate2Repository;
import co.kr.lotteon.repository.cs.CsGroupRepository;
import co.kr.lotteon.repository.cs.CsRepository;
import co.kr.lotteon.repository.member.MemberRepository;
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
import java.util.stream.Collectors;

@Log4j2
@Builder
@RequiredArgsConstructor
@Service
public class AdminCsService {

    private final ModelMapper modelMapper;
    private final CsRepository csRepository;
    private final CsGroupRepository groupRepository;
    private final CsCate1Repository cate1Repository;
    private final CsCate2Repository cate2Repository;
    private final MemberRepository memberRepository;

    //////////////////////////////////////////////////////////////
    // convert List<CsDTO> <---> Page<CsEntity>
    //////////////////////////////////////////////////////////////
    public List<CsDTO> convertToCs(Page<CsEntity> entities) {
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CsDTO.class))
                .collect(Collectors.toList());
    }



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
                        .findByGroupAndParent(group, 0, pageable);
                log.info(" - 2. adminCsList :: group");

                // LIST(where cate1) - 1차유형 글 보기
            } else if (cate2 == null) {
                entities = csRepository
                        .findByCate1AndParent(cate1, 0, pageable);
                log.info(" - 2. adminCsList :: cate1");

                // LIST(where cate2) - 2차유형 글 보기
            } else {
                entities = csRepository
                        .findByCate2AndParent(cate2, 0, pageable);
                log.info(" - 2. adminCsList :: cate2");
            }

            List<CsDTO> dtoList = convertToCs(entities);
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
            int comment = dto.getComment();
            log.info(" - 3. comment : " + comment);

            if(comment > 0) {
                log.info(" - 4. `comment` is greater than 0.");
                CsEntity entity1 = csRepository.findByParent(no);
                log.info(" -  answer is " + (entity1!=null?"OK":"NULL"));

                if(entity1!=null) {
                    log.info(" - 5. answer -> model");
                    model.addAttribute("answer",
                            entity1.toDTO());
                }
            }

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
    // admin/cs/*/write + modify + reply page
    //////////////////////////////////////////////////////////////
    public int adminCsWrite(PageRequestDTO pageRequestDTO) {
        log.info(" ===== adminCsWrite() start =====");

        MemberEntity memberEntity = memberRepository.findById(pageRequestDTO.getUid()).orElse(null);
        log.info(" - 1. memberEntity : " + memberEntity.getUid());
        CsGroupEntity groupEntity = groupRepository.findById(pageRequestDTO.getGroup()).orElse(null);
        log.info(" - 2. groupEntity  : " + groupEntity.getGroup_name());
        CsCate1Entity cate1Entity = cate1Repository.findById(pageRequestDTO.getCate1()).orElse(null);
        log.info(" - 3. cate1Entity  : " + cate1Entity.getCate1_name());
        CsCate2Entity cate2Entity = cate2Repository.findById(pageRequestDTO.getCate2()).orElse(null);
        log.info(" - 4. cate2Entity  : " + cate2Entity.getCate2_name());

        CsEntity result = null;

        log.info(" - 5. type... ");
        if(pageRequestDTO.getNo() > 0) {
            log.info(" - - a. type is modify... ");
            // 게시글 수정
            CsEntity origin = csRepository.findById(pageRequestDTO.getNo()).orElse(null);
            origin.setCate1(cate1Entity);
            origin.setCate2(cate2Entity);
            origin.setTitle(pageRequestDTO.getTitle());
            origin.setContent(pageRequestDTO.getContent());
            result = csRepository.save(origin);
            log.info(" - - .....end");
            return result != null ? result.getNo() : null;

        }else {
            if(pageRequestDTO.getParent() > 0) {
                log.info(" - - b. type is answer... ");
                // 답변 작성
                CsEntity answer = new CsEntity();
                answer.setParent(pageRequestDTO.getParent());
                answer.setGroup(groupEntity);
                answer.setCate1(cate1Entity);
                answer.setCate2(cate2Entity);
                answer.setUid(memberEntity);
                answer.setTitle(pageRequestDTO.getTitle());
                answer.setContent(pageRequestDTO.getContent());
                result = csRepository.save(answer);

                if(result != null) {
                    CsEntity origin = csRepository.findById(pageRequestDTO.getParent()).orElse(null);
                    if(origin != null) {
                        origin.setComment(1);
                        CsEntity rs = csRepository.save(origin);
                        log.info((rs != null)?" - - b-1. comment ++; ":"comment ++ FAILED");
                    }
                }

                log.info(" - - .....end");
                return result != null ? result.getParent() : null;

            }else {
                log.info(" - - c. type is write... ");
                // 게시글 작성
                CsEntity csEntity = new CsEntity();
                csEntity.setGroup(groupEntity);
                csEntity.setCate1(cate1Entity);
                csEntity.setCate2(cate2Entity);
                csEntity.setUid(memberEntity);
                csEntity.setTitle(pageRequestDTO.getTitle());
                csEntity.setContent(pageRequestDTO.getContent());
                result = csRepository.save(csEntity);
                log.info(" - - .....end");
                return result != null ? result.getNo() : null;
            }
        }
    }



    //////////////////////////////////////////////////////////////
    // 게시글 삭제
    //////////////////////////////////////////////////////////////
    public void adminCsDelete(int no) {
        log.info(" - adminCsDelete()... ");
        csRepository.deleteById(no);
    }


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
    public String forAdminCssCSS(Model model, HttpServletRequest request, PageRequestDTO pageRequestDTO) {
        log.info(" ===== forAdminCssCSS() start ===== ");

        String curl = request.getRequestURI();
        log.info(" - 1. 현재주소 : " + curl);

        String[] arr = curl.split("/");
        log.info(" - 2. url 정보 = group : " + arr[4] + ", type : " + arr[5]);

        int pg = pageRequestDTO.getPg();
        log.info(" - 3. 현재 페이지 : " + pg);

        model.addAttribute("group", arr[4]);
        model.addAttribute("type",  arr[5]);
        model.addAttribute("pg",    pg);

        return arr[4]; // group
        /* crul  = /LotteOn/admin/cs/notice/list
           group = {notice, qna, faq};
           type  = {list, view, modify, write, };  */
    }
}