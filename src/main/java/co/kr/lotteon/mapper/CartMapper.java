package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.entity.product.CartEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    public int selectCountCartByUidAndProdNo(String uid, int prodNo);
    public ItemDTO selectProductsForOrder(int cartNo, String uid);
    public int updateCartProductByUidAndProdNo(int count, String uid, int prodNo, int total);
    public int deleteCartProductByCartNo(int cartNo);

    public int deleteCartProductByProdNoAndUid(String uid, int prodNo);

}
