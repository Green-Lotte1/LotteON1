package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminProductMapper {

    public void insertProduct(ProductDTO dto);
    public ProductDTO selectProduct(int prodNo);
    public List<ProductDTO> selectProducts();
    public void updateProduct(ProductDTO dto);
    public void deleteProduct(int prodNo);
}
