package co.kr.lotteon.service;

import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProdCate2Entity;
import co.kr.lotteon.entity.product.ProductEntity;
import co.kr.lotteon.repository.ProdCate1Repository;
import co.kr.lotteon.repository.ProdCate2Repository;
import co.kr.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository prodRepo;
    private final ProdCate1Repository prodCate1Repository;
    private final ProdCate2Repository prodCate2Repository;
    private final ModelMapper modelMapper;

    public PageResponseDTO selectProductByCate1AndCate2(PageRequestDTO pageRequestDTO) {

        log.info("prodService here...1");

        Pageable pageable = pageRequestDTO.getPageable("prodNo");

        log.info("prodService here...2");

        ProdCate1Entity cate1 = prodCate1Repository.findById(pageRequestDTO.getCate1()).orElse(null);

        Page<ProductEntity> result = prodRepo.findByProdCate1AndProdCate2(cate1, pageRequestDTO.getCate2(), pageable);

        log.info("prodService here...3");

        List<ProductDTO> dto = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                .toList();
        int totalElement = (int) result.getTotalElements();

        log.info("prodService here...4");

        log.info(dto.toString());
        log.info(totalElement);

        log.info("prodService here...5");

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dto)
                .total(totalElement)
                .build();
    }

    public List<ProdCate2DTO> selectAllProdCate2() {
        List<ProdCate2Entity> entity = prodCate2Repository.findAll();

        List<ProdCate2DTO> dto = new ArrayList<>();

        if (!(entity == null)) {
            for(ProdCate2Entity toEntity : entity){
                ProdCate2DTO toDto = toEntity.toDTO();
                log.info(toDto.toString());
                dto.add(toDto);
            }
        }

        return dto;
    }

}
