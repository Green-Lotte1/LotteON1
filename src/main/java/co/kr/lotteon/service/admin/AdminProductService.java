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

        List<String> saveNames = fileUpload(dto);

        dto.setThumb1(saveNames.get(0));
        dto.setThumb2(saveNames.get(1));
        dto.setThumb3(saveNames.get(2));
        dto.setDetail(saveNames.get(3));

        adminProductMapper.insertProduct(dto);




    }
    public AdminProductDTO selectProduct(int prodNo){
        return adminProductMapper.selectProduct(prodNo);
    }
    public List<AdminProductDTO> selectProducts(){
        return adminProductMapper.selectProducts();
    }
    public void updateProduct(AdminProductDTO dto){
        adminProductMapper.updateProduct(dto);
    }
    public void deleteProduct(int prodNo){
        adminProductMapper.deleteProduct(prodNo);
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
