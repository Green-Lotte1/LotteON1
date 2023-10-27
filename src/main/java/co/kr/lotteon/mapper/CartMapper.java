package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.entity.product.CartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    public int selectCountCartByUidAndProdNo(@Param("uid")String uid, @Param("prodNo")int prodNo);
    public ItemDTO selectProductsForOrder(@Param("cartNo")int cartNo, @Param("uid")String uid);
    public int updateCartProductByUidAndProdNo(@Param("count")int count,
                                               @Param("uid")String uid,
                                               @Param("prodNo")int prodNo,
                                               @Param("total")int total);
    public int deleteCartProductByCartNo(@Param("cartNo")int cartNo);
    public void deleteCartProductByProdNoAndUid(@Param("uid")String uid, @Param("prodNo")int prodNo);

}
