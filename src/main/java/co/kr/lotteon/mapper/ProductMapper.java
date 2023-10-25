package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Autowired
    MemberDTO MEMBER_DTO();
    @Autowired
    ProdCate1DTO PROD_CATE_1_DTO();
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
