package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
import co.kr.lotteon.dto.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminProductMapper {


   public List<AdminProductDTO> selectPageProducts(int start);


   public int selectProductCountTotal();


    public void insertProduct(AdminProductDTO dto);
    public AdminProductDTO selectProduct(int prodNo);
    public List<AdminProductDTO> selectProducts();
    public void updateProduct(AdminProductDTO dto);
    public void deleteProduct(int prodNo);

    public void UpdateDeleteProduct(int prodNo);

    public List<AdminProductDTO> SearchAdminProducts(@Param("search") String search,
                                                @Param("searchType") String searchType,
                                                @Param("start") int start);

    public int selectSearchCountAdminProducts(@Param("search") String search,
                                              @Param("searchType") String searchType);

    public List<ProdCate2DTO> selectProductCate(int cate1);




}
