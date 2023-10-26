package co.kr.lotteon.service.my;

import co.kr.lotteon.repository.cs.CsRepository;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.product.ReviewRepository;
import co.kr.lotteon.service.cs.CsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyService {

    private  final  CsService         csService;
    private  final  MemberRepository  memberEntity;
    private  final  ReviewRepository  reviewRepository;
    private  final  CsRepository      csRepository;

    public void myPageLayout() {
        /*주문배송(order), 할인쿠폰(coupon), 포인트(member), 문의내역(cs)*/
    }
}