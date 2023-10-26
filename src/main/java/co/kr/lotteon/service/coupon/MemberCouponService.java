package co.kr.lotteon.service.coupon;

import co.kr.lotteon.dto.admin.cs.PageRequestDTO;
import co.kr.lotteon.dto.admin.cs.PageResponseDTO;
import co.kr.lotteon.dto.coupon.MemberCouponDTO;
import co.kr.lotteon.entity.coupon.MemberCouponEntity;
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
    private   final   MemberRepository         memberRepository;
    private   final   MemberService            memberService;
    private   final   ModelMapper              modelMapper;

    public PageResponseDTO myCouponList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<MemberCouponEntity> entities = memberCouponRepository.findByUid(
                                                    memberRepository.findById(
                                                            memberService.MyAccount().getUid())
                                                            .orElse(null), pageable);

        log.info("entities : " + entities);
        log.info("entities : " + entities.getContent());
        log.info("entities : " + entities.getTotalElements());

        List<MemberCouponDTO> list = entities.stream()
                                        .map(entity -> modelMapper
                                                .map(entity, MemberCouponDTO.class))
                                        .collect(Collectors.toList());

        log.info("list : " + list);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .memCoupList(list)
                .total((int) entities.getTotalElements())
                .build();
    }

    public int myCouponCount() {
        return memberCouponRepository.countByUseableCoupon();
    }
}