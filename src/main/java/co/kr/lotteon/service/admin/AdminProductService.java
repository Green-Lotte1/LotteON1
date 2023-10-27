package co.kr.lotteon.service.admin;


import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
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
        filePath += dto.getProdCate1() + "/" + dto.getProdCate2() + "/";
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

    public List<AdminProductDTO> searchProduct(String search, String searchType, int start){
        log.info("SearchProduct...2");
        log.info("searchType : "+searchType);

        List<AdminProductDTO> productList = adminProductMapper.SearchAdminProducts(search, searchType, start);
        log.info("productList Resuslt: "+productList.toString());
        log.info("productList "+productList);

        return productList;
    }

    public int searchProductsCount(String search, String searchType){
        return adminProductMapper.selectSearchCountAdminProducts(search, searchType);
    }

    public List<ProdCate2DTO> SelectProductCate(int cate1){
        List<ProdCate2DTO> cates = adminProductMapper.selectProductCate(cate1);
        return cates;
    }


    //페이징
    public List<AdminProductDTO> selectPageProducts(int start) {
        return adminProductMapper.selectPageProducts(start);
    }

    public int selectProductCountTotal() {


        int pg = adminProductMapper.selectProductCountTotal();
        log.info("페이징=================="+pg);
        return pg;
    }

    // 페이지 마지막 번호
   /* public int getLastPageNum() {
        return getLastPageNum(0);
    }*/

    // 페이지 마지막 번호
    public int getLastPageNum(int total) {

        int lastPageNum = 0;

        if(total % 10 == 0){
            lastPageNum = total / 10;
        }else{
            lastPageNum = total / 10 + 1;
        }

        return lastPageNum;
    }

    // 페이지 그룹
    public int[] getPageGroupNum(int currentPage, int lastPageNum) {
        int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
        int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
        int pageGroupEnd = currentPageGroup * 10;

        if(pageGroupEnd > lastPageNum){
            pageGroupEnd = lastPageNum;
        }

        log.info("getPageGroupNum : " );

        int[] result = {pageGroupStart, pageGroupEnd};

        return result;
    }

    // 페이지 시작번호
    public int getPageStartNum(int total, int currentPage) {
        int start = (currentPage - 1) * 10;
        return total - start;
    }

    // 현재 페이지 번호
    public static int getCurrentPage(String pg) {
        int currentPage = 1;

        if(pg != null){
            currentPage = Integer.parseInt(pg);
        }
    log.info("getCurrentPage : " );
        return currentPage;
    }

    // 현재 페이지 번호
    public int getCurrentPage(Integer pg) {
        int currentPage = 1;

        if(pg != null){
            currentPage = pg;
        }

        return currentPage;
    }


    // Limit 시작번호
    public int getStartNum(int currentPage) {
        return (currentPage - 1) * 10;
    }


}
