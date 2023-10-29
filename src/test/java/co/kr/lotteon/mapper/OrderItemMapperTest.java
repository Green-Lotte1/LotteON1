package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.OrderItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OrderItemMapperTest {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void setOrderItemMapperTest(){

        List<OrderItemDTO> orderItemDTOList = orderItemMapper.selectOrderItemsAll();

        for(OrderItemDTO item : orderItemDTOList){
            System.out.println(item.toString());
        }

    }
}
