package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.product.ProdCate2DTO;
import org.apache.ibatis.annotations.Mapper;

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

    public List<AdminProductDTO> SearchProductProdName(String prodName);
    public List<AdminProductDTO> SearchProductProdNo(String prodNo);
    public List<AdminProductDTO> SearchProductProdCompany(String prodCompany);
    public List<AdminProductDTO> SearchProductSeller(String seller);

    public List<ProdCate2DTO> selectProductCate(int cate1);




}
