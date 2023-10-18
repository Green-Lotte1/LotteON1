package co.kr.lotteon.service;

import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProdCate2Entity;
import co.kr.lotteon.entity.product.ProductEntity;
import co.kr.lotteon.mapper.ProductMapper;
import co.kr.lotteon.repository.ProdCate1Repository;
import co.kr.lotteon.repository.ProdCate2Repository;
import co.kr.lotteon.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository prodRepo;
    private final ProductMapper mapper;
    private final ProdCate1Repository prodCate1Repository;
    private final ProdCate2Repository prodCate2Repository;
    private final ModelMapper modelMapper;

    public ProductDTO registerProduct(ProductDTO productDTO){

      ProductEntity productEntity  = productDTO.toEntity();
      ProductEntity result = prodRepo.save(productEntity);

        return result.toDTO();
    }

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    public ProductDTO fileUpload(ProductDTO dto) {

        log.info("fileUpload...1");
        MultipartFile mf = dto.getDetail();

        log.info("fileUpload...2 : " + mf);

        if(!mf.isEmpty()){
            // 파일 첨부 했을 경우
            String path = new File(filePath).getAbsolutePath();
            log.info("fileUpload...3 : " + path);

            String oName = mf.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString()+ext;

            log.info("fileUpload...4 : " + oName);

            try {
                log.info("fileUpload...5");
                mf.transferTo(new File(path, sName));
                log.info("fileUpload...6");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            log.info("fileUpload...7");
      //      return ProductDTO.builder().ofile(oName).sfile(sName).build();
        }
        log.info("fileUpload...8");
        return null;
    }

    public PageResponseDTO selectProductByCate1AndCate2(PageRequestDTO pageRequestDTO) {
        log.info("prodService here...1");
        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        log.info("prodService here...2");
        ProdCate1Entity cate1 = prodCate1Repository.findById(pageRequestDTO.getProdCate1()).orElse(null);
        log.info("prodService here...3");
        String type = pageRequestDTO.getType();
        Page<ProductEntity> result = null;
        log.info("prodService type : "+type);
        switch (type){
            case "default":
                result = prodRepo.findByProdCate1AndProdCate2(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
            case "sold":
                result = prodRepo.findByProdCate1AndProdCate2OrderBySoldDesc(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
            case "priceAsc":
                result = prodRepo.findByProdCate1AndProdCate2OrderByPriceAsc(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
            case "priceDesc":
                result = prodRepo.findByProdCate1AndProdCate2OrderByPriceDesc(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
            case "score":
                result = prodRepo.findByProdCate1AndProdCate2OrderByScoreDesc(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
            case "review":
                result = prodRepo.findByProdCate1AndProdCate2OrderByReviewDesc(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
            case "rdate":
                result = prodRepo.findByProdCate1AndProdCate2OrderByRdateAsc(cate1, pageRequestDTO.getProdCate2(), pageable);
                break;
        }
        log.info("prodService here...4");
        List<ProductDTO> dto = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                .toList();
        int totalElement = (int) result.getTotalElements();
        log.info("prodService here...5");
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dto)
                .total(totalElement)
                .build();
    }

    public ProductDTO selectProductByProdNo(int prodNo) {
        log.info("view service here...1");
        ProductEntity entity = prodRepo.findById(prodNo).orElse(null);
        log.info("view service here...2");
        return entity.toDTO();
    }

    public void increaseProductHit(int prodNo){
        mapper.increaseProductHit(prodNo);
    }

    public List<ProdCate1DTO> selectAllProdCate1(){
        List<ProdCate1Entity> entity = prodCate1Repository.findAll();

        List<ProdCate1DTO> dto = new ArrayList<>();

        for(ProdCate1Entity toEntity : entity){
            ProdCate1DTO toDto = toEntity.toDTO();
            dto.add(toDto);
        }
        return dto;
    }

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
