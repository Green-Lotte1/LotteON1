package co.kr.lotteon.repository;

import co.kr.lotteon.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {



    @Query()
    public List<ProductEntity> selectProductsByCate1AndCate2(int cate1, int cate2, Pageable pageable);

    // return 배포 할 때 / 넣기로

}
