package co.kr.lotteon.repository;

import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProdCate2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdCate1Repository extends JpaRepository<ProdCate1Entity, Integer> {

}
