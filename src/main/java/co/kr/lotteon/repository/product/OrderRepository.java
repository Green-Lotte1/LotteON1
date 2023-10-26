package co.kr.lotteon.repository.product;

import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.product.CartEntity;
import co.kr.lotteon.entity.product.OrderEntity;
import co.kr.lotteon.entity.product.ProductEntity;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {


}
