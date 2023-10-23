package co.kr.lotteon.service.admin;


import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.mapper.AdminProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Log4j2
@Service
public class AdminProductService {

    @Autowired
    private AdminProductMapper adminProductMapper;

    public void insertProduct(AdminProductDTO dto){

        log.info("insertProd...1");

        List<String> saveNames = fileUpload(dto);

        log.info("insertProd...2");
        dto.setThumb1(saveNames.get(0));
        dto.setThumb2(saveNames.get(1));
        dto.setThumb3(saveNames.get(2));
        dto.setDetail(saveNames.get(3));

        log.info("insertProd...3");
        log.info(dto.toString());
        adminProductMapper.insertProduct(dto);


        log.info("insertProd...4");


    }
    public AdminProductDTO selectProduct(int prodNo){
        return adminProductMapper.selectProduct(prodNo);
    }
    public List<AdminProductDTO> selectProducts(){
        List<AdminProductDTO> productlist = adminProductMapper.selectProducts();
        log.info(productlist.toString());

        return productlist;
    }
    public void updateProduct(AdminProductDTO dto){
        adminProductMapper.updateProduct(dto);
    }
    public void deleteProduct(int prodNo){
        // sale -1 로 변경
        adminProductMapper.UpdateDeleteProduct(prodNo);
    }

    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    public List<String> fileUpload(AdminProductDTO dto) {

        // 파일 첨부 경로
        String path = new File(filePath).getAbsolutePath();
        log.info("fileUpload...3 : " + path);

        // 첨부파일 리스트화
        List<MultipartFile> files = Arrays.asList(
                                      dto.getFileThumb1(),
                                      dto.getFileThumb2(),
                                      dto.getFileThumb3(),
                                      dto.getFileDetail()
                                    );

        List<String> saveNames = new ArrayList<>();

        for (MultipartFile file:files) {
            // 파일명 변경
            String oName = file.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString() + ext;
            log.info("fileUpload...4 : " + oName);
            saveNames.add(sName);

            try {
                log.info("fileUpload...5");
                // 업로드 파일에 saveFile이라는 껍데기 입힘
                file.transferTo(new File(path, sName)); // 저장할 폴더 이름, 저장할 파일 이름
                log.info("fileUpload...6");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            log.info("fileUpload...7");
        }

        return saveNames;
    }

    public List<AdminProductDTO> searchProduct(String search, String searchType){
        log.info("SearchProduct...2");
        log.info("searchType : "+searchType);

        List<AdminProductDTO> searchProductList = null;

        switch (searchType){
            case "prodName":
                searchProductList = adminProductMapper.SearchProductProdName(search);
                break;
            case "prodNo":
                searchProductList = adminProductMapper.SearchProductProdNo(search);
                break;
            case "prodCompany":
                searchProductList = adminProductMapper.SearchProductProdCompany(search);
                break;
            case "seller":
                searchProductList = adminProductMapper.SearchProductSeller(search);
                break;
        }


        log.info("searchProductList :"+searchProductList.toString());

        return searchProductList;
    }
}
/*

    public ProductDTO registerProduct(ProductDTO productDTO){

        log.info("register...2 : " + productDTO);
        log.info("register...3 : " + productDTO);

        return null;
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

                //String newPathName = "/" + dto.getProdCate1().getCate1() + "/" + dto.getProdCate2() +"/"+sName;
                String newPathName = "/";

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
    }   */
