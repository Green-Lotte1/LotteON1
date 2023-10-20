package co.kr.lotteon.service.product;

import co.kr.lotteon.dto.product.CartDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.entity.product.CartEntity;
import co.kr.lotteon.entity.product.ProductEntity;
import co.kr.lotteon.mapper.CartMapper;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.product.CartRepository;
import co.kr.lotteon.repository.product.ProductRepository;
import co.kr.lotteon.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartMapper cartMapper;

    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////////// insertCart
    ///////////////////////////////////////////////////////////////////////////////////
    public int selectCountCartByUidAndProdNo(String uid, int prodNo){
        int result = 0;
        int selectResult = cartMapper.selectCountCartByUidAndProdNo(uid, prodNo);
        log.info("selectResult: "+selectResult);
        if(selectResult > 0){
            result = 1;
        }
        return result;
    }

    public int insertCart(String uid, int prodNo, int inputCount){
        int result = 0;
        log.info("insertCartService here...1");
        ProductEntity product = productRepository.findById(prodNo).orElse(null);
        MemberEntity member = memberRepository.findById(uid).orElse(null);
        log.info("insertCartService here...2");
        // cart에 동일한 상품이 없는 경우
        CartEntity cartEntity = CartEntity.builder()
                .uid(member)
                .prodNo(product)
                .count(inputCount)
                .price(product.getPrice())
                .discount(product.getDiscount())
                .point(product.getPoint())
                .delivery(product.getDelivery())
                .total((product.discountingPrice() * inputCount)+product.getDelivery())
                .build();
        CartEntity cart = cartRepository.save(cartEntity);
        log.info(cart.toString());
        log.info("insertCartService here...3");

        if(cart != null){
            result = 1;
        }
        log.info("insertCartService result: "+ result);
        return result;
    }

    public int updateCart(int count, String uid, int prodNo){
        ProductEntity product = productRepository.findById(prodNo).orElse(null);
        MemberEntity member = memberRepository.findById(uid).orElse(null);
        CartEntity cart = cartRepository.findByUidAndProdNo(member, product);
        int total = (cart.getPrice()*count) - (((cart.getPrice()*count)/100)*cart.getDiscount());
        log.info("updateCart total :"+total);
        int result = cartMapper.updateCartProductByUidAndProdNo(count, uid, prodNo, total);
        log.info("updateCart result :"+result);
        return result;
    }
}
