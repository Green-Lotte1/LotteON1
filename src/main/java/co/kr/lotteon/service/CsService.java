package co.kr.lotteon.service;

import co.kr.lotteon.dto.cs.*;
import co.kr.lotteon.entity.MemberEntity;
import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import co.kr.lotteon.repository.MemberRepository;
import co.kr.lotteon.repository.cs.CsCate1Repository;
import co.kr.lotteon.repository.cs.CsCate2Repository;
import co.kr.lotteon.repository.cs.CsGroupRepository;
import co.kr.lotteon.repository.cs.CsRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Page<CsEntity> entities = csRepository.findByGroupAndParent(group,0, pageable);
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
            page = csRepository.findByGroupAndParent(csGroupEntity,0, pageable);

        }else if(group.equals("faq")) {
            // faq 출력 (cate2마다 출력.)
            log.info("findCsLists (faq)...");

            List<CsCate2Entity> findByCate1 = cate2Repository.findByCate1(cate1Entity);
            log.info("findCsLists findByCate1 : " + findByCate1);

            for(CsCate2Entity ent : findByCate1) {
                Page<CsEntity> pagaFaq = csRepository.findByGroupAndCate1AndCate2AndParent(csGroupEntity, cate1Entity, ent, 0, pageable);
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
            page = csRepository.findByCate1AndParent(cate1Entity, 0, pageable);

            page.forEach(ent -> {
                int no = ent.getNo();
                int cnt = csRepository.countByParent(no);
                String answer = cnt>0?"답변 완료":"검토중";
                ent.setParent(cnt);
                ent.setContent(answer);
                log.info(answer +"(" + cnt + ")");
            });
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



    ////////////////////////////////////////////////////////////////////
    // write page
    ////////////////////////////////////////////////////////////////////
    // cate1_name 출력용
    public List<CsCate1DTO> findByCate(String group) {
        CsGroupEntity entity = groupRepository.findById(group).orElse(null);
        List<CsCate1Entity> entities = cate1Repository.findByGroup(entity);
        List<CsCate1DTO> dto = convertToCate1(entities);
        log.info("findByCate : " + dto);
        return dto;
    }

    // cate2_name 출력용
    public List<CsCate2DTO> findByCate1(String cate1) {
        CsCate1Entity entity = cate1Repository.findById(cate1).orElse(null);
        List<CsCate2Entity> entities = cate2Repository.findByCate1(entity);
        List<CsCate2DTO> dto = convertToCate2(entities);
        log.info("findByCate1 : " + dto);

        return dto;
    }

    // 게시글 작성
    @Transactional
    public int insertQna(PageRequestDTO pageRequestDTO) {
        CsGroupEntity groupEntity  = groupRepository.findById(pageRequestDTO.getGroup()).orElse(null);
        CsCate1Entity cate1Entity  = cate1Repository.findById(pageRequestDTO.getCate1()).orElse(null);
        CsCate2Entity cate2Entity  = cate2Repository.findById(pageRequestDTO.getCate2()).orElse(null);
        MemberEntity  memberEntity = memberRepository.findById(pageRequestDTO.getUid()).orElse(null);

        CsEntity entity = new CsEntity();
        entity.setGroup(groupEntity);
        entity.setCate1(cate1Entity);
        entity.setCate2(cate2Entity);
        entity.setUid(memberEntity);
        entity.setTitle(pageRequestDTO.getTitle());
        entity.setContent(pageRequestDTO.getContent());

        CsEntity result = csRepository.save(entity);

        return (result != null)? 1:0;
    }
}
