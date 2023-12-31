package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.OrderItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {


    public int insertOrderItem(ItemDTO item);

    public List<ItemDTO> selectOrderItemsByOrdNo(@Param("ordNo")int ordNo);

    public List<OrderItemDTO> selectOrderItemsAll();

}
