package co.kr.lotteon.repository;

import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {



    public Page<ProductEntity> findByProdCate1AndProdCate2(ProdCate1Entity cate1, int cate2, Pageable pageable);


    // return 배포 할 때 / 넣기로

}
