package co.kr.lotteon.service.product;

import co.kr.lotteon.dto.member.MemberDTO;
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
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartMapper cartMapper;
    private final ModelMapper modelMapper;

    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////////// insertCart
    ///////////////////////////////////////////////////////////////////////////////////
    public int selectCountCartByUidAndProdNo(Model model, String uid, int prodNo){
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


    public List<CartDTO> selectAllCartByUid(MemberDTO member){
        MemberEntity memberEntity = member.toEntity();
        List<CartEntity> cartEntity = cartRepository.findByUidOrderByRdateDesc(memberEntity);

        List<CartDTO> cartList = cartEntity.stream()
                .map(entity -> modelMapper.map(entity, CartDTO.class))
                .collect(Collectors.toList());

        log.info(cartList.toString());

        return cartList;
    }

    public int deleteCartProductByCartNo(String[] cartNos){

        int result = 0;
        for(int i = 1; i < cartNos.length; i++){
            int cartNo = Integer.parseInt(cartNos[i]);
            result = cartMapper.deleteCartProductByCartNo(cartNo);
        }
        log.info("deleteCart result: "+result);
        return  result;
    }
}
