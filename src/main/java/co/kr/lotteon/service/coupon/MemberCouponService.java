package co.kr.lotteon.service.coupon;

import co.kr.lotteon.dto.admin.cs.PageRequestDTO;
import co.kr.lotteon.dto.admin.cs.PageResponseDTO;
import co.kr.lotteon.dto.coupon.CouponDTO;
import co.kr.lotteon.dto.coupon.MemberCouponDTO;
import co.kr.lotteon.entity.coupon.MemberCouponEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.mapper.CouponMapper;
import co.kr.lotteon.repository.coupon.MemberCouponRepository;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private   final   MemberCouponRepository   memberCouponRepository;
    private   final   MemberService            memberService;
    private   final   CouponMapper             couponMapper;

    public PageResponseDTO myCouponList(PageRequestDTO pageRequestDTO) {

        String username = memberService.MyAccount().getUid();
        int    pg       = pageRequestDTO.getPg();
        String status   = pageRequestDTO.getStatus();

        log.info("username : " + username);
        log.info("pg       : " + pg);
        log.info("status   : " + status);

        List<CouponDTO> list = couponMapper.myCouponList(username,(pg-1)*10, status);
        int total = couponMapper.myCouponTotal(username, status);
        int mycpn = couponMapper.myCouponTotal(username, "useable");

        log.info("list  : " + list);
        log.info("total : " + total);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .myCoupon(list)
                .total(total)
                .no(mycpn)
                .build();
    }

    public int nav_myCoupon() {
        return couponMapper.myCouponTotal(memberService.MyAccount().getUid(), "useable");
    }
}