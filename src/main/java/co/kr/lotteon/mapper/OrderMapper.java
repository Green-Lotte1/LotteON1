package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {


    public int insertOrder(OrderDTO orderDTO);

    public int selectLatestOrdNo(String uid);


}
