package co.kr.lotteon.mapper;

import co.kr.lotteon.entity.product.CartEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {

    public int selectCountCartByUidAndProdNo(String uid, int prodNo);
    public int updateCartProductByUidAndProdNo(int count, String uid, int prodNo, int total);
    public int deleteCartProductByCartNo(int cartNo);

}
