package co.kr.lotteon.repository.product;

import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.product.CartEntity;
import co.kr.lotteon.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    public int countByUidAndProdNo(MemberEntity uid, ProductEntity prodNo);

    public CartEntity findByUidAndProdNo(MemberEntity uid, ProductEntity prodNo);

    public List<CartEntity> findByUidOrderByRdateDesc(MemberEntity uid);

    /*@Modifying(clearAutomatically = true)
    @Query("update km_product_cart c set c.count=c.count+ ?2 where c.prodNo= ?3 and c.uid = ?1")
    public int updateByUidCartProduct(String uid, int count, int prodNo);*/
}
