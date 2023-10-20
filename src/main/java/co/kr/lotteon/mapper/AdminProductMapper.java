package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.admin.AdminProductDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminProductMapper {

    public void insertProduct(AdminProductDTO dto);
    public AdminProductDTO selectProduct(int prodNo);
    public List<AdminProductDTO> selectProducts();
    public void updateProduct(AdminProductDTO dto);
    public void deleteProduct(int prodNo);

}
