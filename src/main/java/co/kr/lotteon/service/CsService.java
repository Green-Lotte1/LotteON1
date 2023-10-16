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
        List<CsCate1Entity> entity = cate1Repository.findByGroup(group);
        List<CsCate1DTO> dto = convertToCate1(entity);

        return dto;
    }

    // group info 출력
    public CsGroupDTO groupInfo(String group) {
        CsGroupEntity result = groupRepository.findById(group).orElse(null);
        CsGroupDTO dto = result.toDTO();

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
    // list page
    ////////////////////////////////////////////////////////////////////

    // 게시글 출력하기
    public PageResponseDTO findCsLists(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("no");
        String group = pageRequestDTO.getGroup();
        log.info("testdebug group : " + group);
        String cate1 = pageRequestDTO.getCate1();
        log.info("testdebug cate1 : " + cate1);
        String cate2 = pageRequestDTO.getCate2();
        log.info("testdebug cate2 : " + cate2);

        List<CsDTO> dto = null;
        int totalElement = 0;

        if(group.equals("notice") && cate1.equals("101")) {
            // notice 전체 출력
            CsGroupEntity csGroupEntity = groupRepository.findById(group).orElse(null);
            Page<CsEntity> page = csRepository.findByGroupAndParent(csGroupEntity,0, pageable);

            dto = page.getContent()
                    .stream()
                    .map(entity -> modelMapper.map(entity, CsDTO.class))
                    .toList();

            totalElement = (int) page.getTotalElements();
            
            
        }else if(group.equals("faq")) {
            // faq 출력
            // cate2마다 2개씩 출력.
            log.info("when group faq");

        }else {
            // 나머지 cate1을 기준으로 list 출력
            log.info("when group default");
        }

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