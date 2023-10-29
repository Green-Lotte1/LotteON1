package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.OrderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {


    public int insertOrder(OrderDTO orderDTO);

    public int selectLatestOrdNo(@Param("ordUid")String ordUid);

    public List<OrderDTO> myOrderList(@Param("ordUid")String ordUid, @Param("pg")int pg, @Param("type1")String type1, @Param("type2")String type2);

    public int myOrderTotal(@Param("ordUid")String ordUid, @Param("type1")String type1, @Param("type2")String type2);

}
