package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdminProductMapperTest {

    @Autowired
    private AdminProductMapper adminProductMapper;

    @Test
    public void searchTest(){
        /*List<> productDTOS = adminProductMapper.SearchAdminProducts(
                "자바","prodName",0
        );

        for(ProductDTO product:productDTOS){
            System.out.println(product.toString());
        }*/
    }
}
