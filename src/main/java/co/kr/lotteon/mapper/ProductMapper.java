package co.kr.lotteon.mapper;

import co.kr.lotteon.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    public ProductEntity selectProduct();

}
