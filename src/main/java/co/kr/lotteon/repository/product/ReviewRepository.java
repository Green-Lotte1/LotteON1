package co.kr.lotteon.repository.product;

import co.kr.lotteon.entity.product.ProductEntity;
import co.kr.lotteon.entity.product.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    public Page<ReviewEntity> findByProdNoOrderByRdateAsc(ProductEntity prodNo, Pageable pageable);
}
