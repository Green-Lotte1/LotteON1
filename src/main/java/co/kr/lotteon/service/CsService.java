package co.kr.lotteon.service;

import co.kr.lotteon.dto.cs.*;
import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
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


    ////////////////////////////////////////////////////////////////////
    // list page
    ////////////////////////////////////////////////////////////////////
    // 리스트 출력하기
    public PageResponseDTO findCsLists(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<CsEntity> page = null;
        List<List<CsDTO>> faq = new ArrayList<>();
        int totalElement = 0;

        String group = pageRequestDTO.getGroup();
        log.info("findCsLists group : " + group);
        String cate1 = pageRequestDTO.getCate1();
        log.info("findCsLists cate1 : " + cate1);
        int cate2 = Integer.parseInt(pageRequestDTO.getCate2());
        log.info("findCsLists cate2 : " + cate2);

        CsCate1Entity cate1Entity = cate1Repository.findById(cate1).orElse(null);
        log.info("findCsLists cate1Entity : " + cate1Entity);

        CsGroupEntity csGroupEntity = groupRepository.findById(group).orElse(null);
        log.info("findCsLists csGroupEntity : " + csGroupEntity);

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

}
