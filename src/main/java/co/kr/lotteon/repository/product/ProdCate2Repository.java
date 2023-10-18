package co.kr.lotteon.repository.product;

import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProdCate2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdCate2Repository extends JpaRepository<ProdCate2Entity, Integer> {

    //////////////////////////////
    ////////    카테고리 이름 출력
    //////////////////////////////

    public ProdCate2Entity findByCate1AndCate2(ProdCate1Entity cate1, int cate2);
}
