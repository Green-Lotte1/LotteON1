package co.kr.lotteon.service.my;

import co.kr.lotteon.dto.Utils;
import co.kr.lotteon.dto.member.PointDTO;
import co.kr.lotteon.dto.my.PageRequestMyDTO;
import co.kr.lotteon.dto.my.PageResponseMyDTO;
import co.kr.lotteon.dto.product.OrderDTO;
import co.kr.lotteon.mapper.CouponMapper;
import co.kr.lotteon.mapper.OrderMapper;
import co.kr.lotteon.mapper.PointMapper;
import co.kr.lotteon.repository.cs.CsRepository;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.product.ReviewRepository;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class MyService {

    private  final  PointMapper       pointMapper;
    private  final  OrderMapper       orderMapper;
    private  final  CouponMapper      couponMapper;

    private  final  CsService         csService;
    private  final  MemberService     memberService;

    private  final  MemberRepository  memberEntity;
    private  final  ReviewRepository  reviewRepository;
    private  final  CsRepository      csRepository;

    public void myPageLayout(Model model) {
        String uid = memberService.MyAccount().getUid();
        int orderTotal  = orderMapper.myOrderTotal(uid, "count", "count");
        int couponTotal = couponMapper.myCouponTotal(uid, "useable");
        int pointTotal  = pointMapper.myPointTotal(uid);
        int qnaTotal    = csService.myQnaTotal(uid);

        String tot = Utils.comma(pointTotal);

        model.addAttribute("orderTotal",  orderTotal);
        model.addAttribute("couponTotal", couponTotal);
        model.addAttribute("pointTotal",  tot);
        model.addAttribute("qnaTotal",    qnaTotal);

        /*주문배송(order), 할인쿠폰(coupon), 포인트(member), 문의내역(cs)*/
    }

    public PageResponseMyDTO myPointList(PageRequestMyDTO pageRequestMyDTO) {
        log.info("myPointList() start!");
        String uid   = pageRequestMyDTO.getUid();
        int    pg    = pageRequestMyDTO.getPg();
        String type1 = pageRequestMyDTO.getType1();
        String type2 = pageRequestMyDTO.getType2();

        log.info(" - 1. uid   : " + uid);
        log.info(" - 2. pg    : " + pg);
        log.info(" - 3. type1 : " + type1);
        log.info(" - 4. type2 : " + type2);

        List<PointDTO> list  = pointMapper.myPointList(uid, type1, type2, (pg-1)*10);
        int            total = pointMapper.myPointCount(uid, type1, type2);

        log.info(" - 5. list  : " + list);
        log.info(" - 6. total : " + total);

        return PageResponseMyDTO.builder()
                .pageRequestMyDTO(pageRequestMyDTO)
                .list(list)
                .total(total)
                .build();
    }

    public PageResponseMyDTO myOrderList(PageRequestMyDTO pageRequestMyDTO) {
        log.info("myOrderList() start!");
        String uid   = pageRequestMyDTO.getUid();
        int    pg    = pageRequestMyDTO.getPg();
        String type1 = pageRequestMyDTO.getType1();
        String type2 = pageRequestMyDTO.getType2();

        log.info(" - 1. uid   : " + uid);
        log.info(" - 2. pg    : " + pg);
        log.info(" - 3. type1 : " + type1);
        log.info(" - 4. type2 : " + type2);

        List<OrderDTO> list  = orderMapper.myOrderList(uid,(pg-1)*10, type1, type2);
        int            total = orderMapper.myOrderTotal(uid, type1, type2);

        return PageResponseMyDTO.builder()
                .pageRequestMyDTO(pageRequestMyDTO)
                .ordList(list)
                .total(total)
                .build();
    }
}