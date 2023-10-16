package co.kr.lotteon.service;

import co.kr.lotteon.dto.cs.*;
import co.kr.lotteon.entity.cs.CsCate1Entity;
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

import java.util.List;
import java.util.Optional;
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
        List<CsCate1Entity> entity = null;
        if (group != null) {
            entity = cate1Repository.findByGroup(group);
        }
        List<CsCate1DTO> dto = null;
        if (entity != null) {
            dto = convertToCate1(entity);
        }

        return dto;
    }

    // group info 출력
    public CsGroupDTO groupInfo(String group) {
        CsGroupEntity result = groupRepository.findById(group).orElse(null);
        CsGroupDTO dto = null;
        if (result != null) {
            dto = result.toDTO();
        }

        return dto;
    }


    // cate1 Info 출력
    public CsCate1DTO cate1Info(PageRequestDTO pageRequestDTO) {
        String cate1 = pageRequestDTO.getCate1();
        log.info("cate1 : " + cate1);
        CsCate1Entity entity = cate1Repository.findByCate1(cate1);
        log.info("cate1Info Method : " + entity);

        return entity.toDTO();
    }

    ////////////////////////////////////////////////////////////////////
    // view page
    ////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////
    // list page
    ////////////////////////////////////////////////////////////////////
    // 게시글 출력하기
    public PageResponseDTO findCsLists(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("no");
        String group = pageRequestDTO.getGroup();
        log.info("testdebug group : " + group);
        String cate1 = pageRequestDTO.getCate1();
        log.info("testdebug cate1 : " + cate1);
        int cate2 = Integer.parseInt(pageRequestDTO.getCate2());
        log.info("testdebug cate2 : " + cate2);

        Page<CsEntity> page = null;
        List<List<CsDTO>> faq = null;
        int totalElement = 0;
        CsGroupEntity csGroupEntity = groupRepository.findById(group).orElse(null);

        // 게시글 출력 시작
        if(group.equals("notice") && cate1.equals("101")) {
            // notice 전체 출력
            log.info("when notice all");
            page = csRepository.findByGroupAndParent(csGroupEntity,0, pageable);

        }else if(group.equals("faq")) {
            // faq 출력 (cate2마다 2개씩 출력.)
            log.info("when faq");
            int cate2EA = csRepository.countByCate1(cate1);
            for(int i=1; i<=cate2EA; i++) {
                List<CsEntity> pages = csRepository.findByGroupAndCate1AndCate2AndParent(csGroupEntity, cate1, cate2, 0);
                faq.add(convertToCs(pages));
            }
            log.info("result : " + faq);
            return PageResponseDTO.builder()
                    .pageRequestDTO(pageRequestDTO)
                    .csLists(faq)
                    .total(totalElement)
                    .build();

        }else {
            // 나머지 cate1을 기준으로 list 출력
            log.info("when default");
            page = csRepository.findByCate1AndParent(cate1, 0, pageable);
        }

        totalElement = (int) page.getTotalElements();
        log.info("totalElement : " + totalElement);
        List<CsDTO> dto = page.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, CsDTO.class))
                .toList();
        log.info("result : " + dto);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .csList(dto)
                .total(totalElement)
                .build();
    }



    ////////////////////////////////////////////////////////////////////
    // view page
    ////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////
    // write page
    ////////////////////////////////////////////////////////////////////

}
