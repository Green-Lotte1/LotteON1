package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.product.ItemDTO;
import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    public ItemDTO selectProductForOrder(@Param("prodNo") int prodNo);

    public void increaseProductHitByProdNo(@Param("prodNo") int prodNo);

    public int selectLatestOrdNo(@Param("ordUid") String ordUid);

    public void minusStock(@Param("prodNo") int prodNo,@Param("count") int count);




    public List<ProductDTO> search(@Param("keyword") String keyword,
                                   @Param("prodCate1")int prodCate1,
                                   @Param("type")String type,
                                   @Param("start")int start,
                                   @Param("chkProdName")String chkProdName,
                                   @Param("chkProdDesc")String chkProdDesc,
                                   @Param("chkProdPrice")String chkProdPrice,
                                   @Param("min")int min,
                                   @Param("max")int max
                                );

    public int selectSearchCountProducts(@Param("keyword")String keyword,
                                         @Param("prodCate1")int prodCate1,
                                         @Param("chkProdName")String chkProdName,
                                         @Param("chkProdDesc")String chkProdDesc,
                                         @Param("chkProdPrice")String chkProdPrice,
                                         @Param("min")int min,
                                         @Param("max")int max
                                         );

}
