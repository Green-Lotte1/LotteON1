package co.kr.lotteon.service.product;

import co.kr.lotteon.dto.product.CartDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.product.CartEntity;
import co.kr.lotteon.entity.product.ProductEntity;
import co.kr.lotteon.repository.product.CartRepository;
import co.kr.lotteon.repository.product.ProductRepository;
import co.kr.lotteon.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////////// insertCart
    ///////////////////////////////////////////////////////////////////////////////////
    public int selectCountCartByUidAndProdNo(MemberEntity member, int prodNo){
        int result = 0;
        ProductEntity product = productRepository.findById(prodNo).orElse(null);
        int selectResult = cartRepository.countByUidAndProdNo(member, product);
        if(selectResult > 0){
            result = 1;
        }
        return result;
    }

    /*public void insertCart(MyUserDetails member, int prodNo, int inputCount, int result){

        String uid = member.getMember().getUid();
        ProductEntity product = productRepository.findById(prodNo).orElse(null);
        // cart에 동일한 상품이 없는 경우
        if(result < 1){


            CartEntity cartEntity = CartEntity.builder()
                    .uid(product.getSeller())
                    .prodNo(product)
                    .count(inputCount)
                    .price(product.discountingPrice())
                    .discount(product.getDiscount())
                    .point(product.getPoint())
                    .delivery(product.getDelivery())
                    .total(product.discountingPrice() * inputCount)
                    .build();
            cartRepository.save(cartEntity);
        // cart에 동일한 상품이 이미 있는 경우
        }else if(result > 0){
            cartRepository.updateByUidCartProduct(uid, inputCount, prodNo);
        }
    }*/
}
