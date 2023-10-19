package co.kr.lotteon.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {

    public int selectCountCartByUidAndProdNo(String uid, int prodNo);
    public void updateCartProductByUidAndProdNo(int count, String uid, int prodNo);

}
