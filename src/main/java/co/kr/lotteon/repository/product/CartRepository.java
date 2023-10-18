package co.kr.lotteon.repository.product;

import co.kr.lotteon.entity.product.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    /*public int countByUidAndProdNo(String uid, int prodNo);*/
}
