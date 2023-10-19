package co.kr.lotteon.service.product;

import co.kr.lotteon.dto.product.*;
import co.kr.lotteon.entity.product.*;
import co.kr.lotteon.mapper.ProductMapper;
import co.kr.lotteon.repository.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    
    ////////////////////////////////////////////////////////////////////
    ////////////// admin
    ////////////////////////////////////////////////////////////////////
    ////////////// 상품 등록
    ////////////////////////////////////////////////////////////////////
    public ProductDTO registerProduct(ProductDTO productDTO){

        log.info("register...2 : " + productDTO);
        ProductEntity productEntity  = productDTO.toEntity();
        ProductEntity result = prodRepo.save(productEntity);
        log.info("register...3 : " + productDTO);

        return result.toDTO();
    }

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    public void fileUpload(ProductDTO dto) {

        log.info("fileUpload...1");
        // multipartFile에 초기화
        List<MultipartFile> files = new ArrayList<>();
        files.add(dto.getThumb1());
        files.add(dto.getThumb2());
        files.add(dto.getThumb3());
        files.add(dto.getDetail());
        log.info("fileUpload...2");
        fileUpload(files, dto);
        log.info("END");

    }

    public void fileUpload(List<MultipartFile> files, ProductDTO dto) {
        // 파일이 존재할 때.
        if(!files.isEmpty()){

            // 파일 첨부 경로
            String path = new File(filePath).getAbsolutePath();
            log.info("fileUpload...3 : " + path);

            for (MultipartFile file:files) {
                // 파일명 변경
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;
                log.info("fileUpload...4 : " + oName);

                String newPathName = "/" + dto.getProdCate1().getCate1() + "/" + dto.getProdCate2() +"/"+sName;

                try {
                    log.info("fileUpload...5");
                    // 업로드 파일에 saveFile이라는 껍데기 입힘
                    file.transferTo(new File(newPathName, sName)); // 저장할 폴더 이름, 저장할 파일 이름
                    log.info("fileUpload...6");
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                log.info("fileUpload...7");
            }
        }
        log.info("fileUpload...8");
    }
  
    public ProdCate1Entity select_Cate1(int prodCate1) {
        ProdCate1Entity entity = prodCate1Repository.findById(prodCate1).orElse(null);
        return null;
    }

    
    ////////////////////////////////////////////////////////////////////
    ////////////// product
    ////////////////////////////////////////////////////////////////////
    //////////////////////// 
    ////////////////////////////////////////////////////////////////////
    
    //////////////////////////////
    ////////    product list 카테고리 및 type에 맞게 가져오기
    //////////////////////////////
    public PageResponseDTO selectProductByCate1AndCate2(PageRequestDTO pageRequestDTO) {
        log.info("prodService here...1");
        Pageable pageable = pageRequestDTO.getPageable("prodNo");
        log.info("prodService here...2");
        ///// 현재 페이지의 cate1
        int cate1 = pageRequestDTO.getProdCate1();
        log.info("cate1 : "+ cate1);
        ProdCate1Entity cate1Entity = null;
        ///// 현재 페이지가 전체 상품 리스트가 아닐때만 전체 cate1Entity를 가져온다
        if(!(cate1 == 1)){
            cate1Entity = prodCate1Repository.findById(cate1).orElse(null);
        }
        log.info("prodService here...3");
        ///// 현재 페이지 리스트의 분류 타입
        String type = pageRequestDTO.getType();
        ///// 현재 페이지의 cate2
        int cate2 = pageRequestDTO.getProdCate2();
        log.info("cate2 : "+ cate2);
        ///// ProductEntity 전체를 담을 result 생성
        Page<ProductEntity> result = null;
        log.info("prodService type : "+type);
        String nav = "히트 상품";

        ///// 현재 페이지가 전체 상품 리스트라면
        if (cate1 == 1 && cate2 == 1) {
            switch (type){
                case "hit":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByHitDesc(1, 1, pageable);
                    nav = "히트 상품";
                    break;
                case "recommend":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByScoreDesc(1, 1, pageable);
                    nav = "추천 상품";
                    break;
                case "latest":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByRdateAsc(1, 1, pageable);
                    nav = "최신 상품";
                    break;
                case "hot":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderBySoldDesc(1, 1, pageable);
                    nav = "인기 상품";
                    break;
                case "discount":
                    result = prodRepo.findByStockGreaterThanEqualAndSaleEqualsOrderByDiscountDesc(1, 1, pageable);
                    nav = "할인 상품";
                    break;
            }
        ///// 현재 상품 페이지가 전체 상품 페이지가 아니라면
        }else if(!(cate1 == 1 && cate2 == 1)){
            switch (type){
                case "default":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEquals(cate1Entity, cate2, 1, 1, pageable);
                    break;
                case "sold":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderBySoldDesc(cate1Entity, cate2, 1, 1, pageable);
                    break;
                case "priceAsc":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByPriceAsc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "priceDesc":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByPriceDesc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "score":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByScoreDesc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "review":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByReviewDesc(cate1Entity, cate2,  1, 1, pageable);
                    break;
                case "rdate":
                    result = prodRepo.findByProdCate1AndProdCate2AndStockGreaterThanEqualAndSaleEqualsOrderByRdateAsc(cate1Entity, cate2,  1, 1, pageable);
                    break;
            }
        }

        log.info("prodService here...4");
        ///// 위에서 담아온 페이지 리스트를 stream으로 ProductDto리스트 생성
        List<ProductDTO> dto = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ProductDTO.class))
                .toList();
        ///// 불러온 ProductDto의 총 갯수
        int totalElement = (int) result.getTotalElements();
        log.info("prodService here...5");
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dto)
                .total(totalElement)
                .build();
    }

    //////////////////////////////
    ////////    product view
    //////////////////////////////
    public ProductDTO selectProductByProdNo(int prodNo) {
        ProductEntity entity = prodRepo.findById(prodNo).orElse(null);
        return entity.toDTO();
    }

    //////////////////////////////
    ////////    product 해당 review 불러오기
    //////////////////////////////
    public PageResponseDTO selectReviewByProdNo(PageRequestDTO pageRequestDTO){
        ProductEntity prodEntity = prodRepo.findById(pageRequestDTO.getProdNo()).orElse(null);
        pageRequestDTO.setSize(5);
        Pageable pageable = pageRequestDTO.getPageable("revNo");
        Page<ReviewEntity> result = reviewRepository.findByProdNoOrderByRdateAsc(prodEntity, pageable);
        List<ReviewDTO> dto = result.getContent()
                                .stream()
                                .map(entity -> modelMapper.map(entity, ReviewDTO.class))
                                .toList();
        int totalElement = (int) result.getTotalElements();
        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .reviewList(dto)
                .prodNo(pageRequestDTO.getProdNo())
                .total(totalElement)
                .build();
    }

    public void increaseProductHit(int prodNo){
        mapper.increaseProductHit(prodNo);
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////// 카테고리
    ////////////////////////////////////////////////////////////////////
    
    //////////////////////////////
    ////////    product aside cate1 가져오기
    //////////////////////////////
    public List<ProdCate1DTO> selectAllProdCate1(){
        List<ProdCate1Entity> entity = prodCate1Repository.findAll();

        List<ProdCate1DTO> dto = new ArrayList<>();

        for(ProdCate1Entity toEntity : entity){
            ProdCate1DTO toDto = toEntity.toDTO();
            dto.add(toDto);
        }
        return dto;
    }

    //////////////////////////////
    ////////    product 전체 cate 가져오기
    //////////////////////////////
    public List<ProdCate2DTO> selectAllProdCate1AndProdCate2() {
        List<ProdCate2Entity> entity = prodCate2Repository.findAll();

        List<ProdCate2DTO> dto = new ArrayList<>();

        for(ProdCate2Entity toEntity : entity){
            ProdCate2DTO toDto = toEntity.toDTO();
            dto.add(toDto);
        }

        return dto;
    }
  
    

    //////////////////////////////
    ////////    product list 현재 페이지 nav cate1과 cate2 가져오기
    //////////////////////////////
    public ProdCate2DTO selectAllProdCateByCate2(int cate1, int cate2){
        ProdCate1Entity cate1Entity = prodCate1Repository.findById(cate1).orElse(null);
        return prodCate2Repository.findByCate1AndCate2(cate1Entity, cate2).toDTO();
    }































    public String loginStatus() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("username : " + SecurityContextHolder.getContext().getAuthentication().getName());
        return username;
    }
}
