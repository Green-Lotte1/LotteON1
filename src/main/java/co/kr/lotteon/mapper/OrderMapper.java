package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {


    public int insertOrder(OrderDTO orderDTO);

    public int selectLatestOrdNo(@Param("ordUid")String ordUid);


}
