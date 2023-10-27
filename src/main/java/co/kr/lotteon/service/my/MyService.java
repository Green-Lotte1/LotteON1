package co.kr.lotteon.service.my;

import co.kr.lotteon.dto.member.PointDTO;
import co.kr.lotteon.dto.my.PageRequestMyDTO;
import co.kr.lotteon.dto.my.PageResponseMyDTO;
import co.kr.lotteon.mapper.PointMapper;
import co.kr.lotteon.repository.cs.CsRepository;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.product.ReviewRepository;
import co.kr.lotteon.service.cs.CsService;
import co.kr.lotteon.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class MyService {

    private  final  PointMapper       pointMapper;

    private  final  CsService         csService;
    private  final  MemberService     memberService;

    private  final  MemberRepository  memberEntity;
    private  final  ReviewRepository  reviewRepository;
    private  final  CsRepository      csRepository;

    public void myPageLayout() {


        /*주문배송(order), 할인쿠폰(coupon), 포인트(member), 문의내역(cs)*/
    }


    public PageResponseMyDTO myPointList(PageRequestMyDTO pageRequestMyDTO) {
        String uid   = pageRequestMyDTO.getUid();
        int    pg    = pageRequestMyDTO.getPg();
        String type1 = pageRequestMyDTO.getType1();
        String type2 = pageRequestMyDTO.getType2();

        log.info("uid   : " + uid);
        log.info("pg    : " + pg);
        log.info("type1 : " + type1);
        log.info("type2 : " + type2);

        List<PointDTO> list  = pointMapper.myPointList(uid, type1, type2, (pg-1)*10);
        int            total = pointMapper.myPointCount(uid, type1, type2);

        return PageResponseMyDTO.builder()
                .pageRequestMyDTO(pageRequestMyDTO)
                .list(list)
                .total(total)
                .build();
    }
}