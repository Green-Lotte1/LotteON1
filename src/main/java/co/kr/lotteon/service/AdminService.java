package co.kr.lotteon.service;


import co.kr.lotteon.dto.product.PageRequestDTO;
import co.kr.lotteon.dto.product.PageResponseDTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProdCate2Entity;
import co.kr.lotteon.entity.product.ProductEntity;
import co.kr.lotteon.repository.MemberRepository;
import co.kr.lotteon.repository.ProdCate1Repository;
import co.kr.lotteon.repository.ProdCate2Repository;
import co.kr.lotteon.repository.ProductRepository;
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


@Log4j2
@Builder
@RequiredArgsConstructor
@Service
public class AdminService {

    private final ModelMapper modelMapper;
    private final CsRepository csRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProdCate1Repository prodCate1Repository;
    private final ProdCate2Repository prodCate2Repository;

    public PageResponseDTO findByParentAndCate(PageRequestDTO pageRequestDTO){

       Pageable pageable = pageRequestDTO.getPageable("prodNo");

        ProdCate1Entity cate1 = prodCate1Repository.findById(pageRequestDTO.getProdCate1()).orElse(null);
        Page<ProductEntity> result = productRepository. findByProdCate1AndProdCate2(cate1, pageRequestDTO.getProdCate2(), pageable);

        List<ProductDTO> dtoList = result.getContent()
                                        .stream()
                                        .map(entity -> modelMapper.map(entity, ProductDTO.class))
                                        .toList();
        int totalElement = (int) result.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(totalElement)
                .build();
    }

 //  public void save(ProductDTO dto) {


   //     productRepository.save(dto.toEntity());


   // }

    public List<ProdCate2DTO> selectAllProdCate1AndProdCate2() {
        List<ProdCate2Entity> entity = prodCate2Repository.findAll();

        List<ProdCate2DTO> dto = new ArrayList<>();

        for(ProdCate2Entity toEntity : entity){
            ProdCate2DTO toDto = toEntity.toDTO();
            dto.add(toDto);
        }

        return dto;
    }



}
