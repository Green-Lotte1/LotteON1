package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    /*
    public ProductEntity selectProduct(int prodNo);
*/

    public ItemDTO selectProductForOrder(int prodNo);

    public void increaseProductHitByProdNo(int prodNo);

    public int selectLatestOrdNo(String ordUid);

}
