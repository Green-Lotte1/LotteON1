package co.kr.lotteon.service;

import co.kr.lotteon.dto.PageRequestDTO;
import co.kr.lotteon.dto.PageResponseDTO;
import co.kr.lotteon.entity.ProductEntity;
import co.kr.lotteon.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Log4j2
@Service
public class ProductService {

    @Autowired
    private ProductRepository prodRepo;

//    public PageResponseDTO selectProductByCate1AndCate2(PageRequestDTO pageRequestDTO) {
//
//        Pageable pageable = pageRequestDTO.getPageable("no");
//
//        Page<ProductEntity> result = prodRepo.selectProductsByCate1AndCate2(0, pageRequestDTO.getCate(), pageable)
//
//        return null;
//    }

}
