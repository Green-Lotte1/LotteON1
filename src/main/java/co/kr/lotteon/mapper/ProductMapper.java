package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    /*
    public ProductEntity selectProduct(int prodNo);
*/

    public ItemDTO selectProductForOrder(int prodNo);

    public void increaseProductHitByProdNo(int prodNo);

    public int selectLatestOrdNo(String ordUid);

    public void minusStock(int prodNo, int count);


    public List<ProductDTO> search(String keyword);

    public List<ProductDTO> search(String keyword, int prodCate1);

    public List<ProductDTO> search(String keyword, int prodCate1, String type);

}
