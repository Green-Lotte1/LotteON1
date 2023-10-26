package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;




    public void search(){


        List<ProductDTO> result  = productMapper.search("50", 10, "default", 1);



        for(ProductDTO dto : result){
            System.out.println(dto);
        }

    }
    @Test
    public void selectSearchCountProducts(){


        int result = productMapper.selectSearchCountProducts("50",
                                                            0,
                                                            false,
                                                            false,
                                                            true,
                                                                10,
                                                                50000
                                                            );
        System.out.println("test: "+result);

    }

}
