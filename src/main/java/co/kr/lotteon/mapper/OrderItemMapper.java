package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.OrderItemDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {


    public int insertOrderItem(OrderItemDTO orderItem);


}
