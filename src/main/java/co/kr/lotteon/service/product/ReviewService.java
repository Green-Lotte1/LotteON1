package co.kr.lotteon.service.product;

import co.kr.lotteon.dto.product.PageRequestDTO;
import co.kr.lotteon.dto.product.PageResponseDTO;
import co.kr.lotteon.dto.product.ReviewDTO;
import co.kr.lotteon.entity.product.ReviewEntity;
import co.kr.lotteon.repository.member.MemberRepository;
import co.kr.lotteon.repository.product.ReviewRepository;
import co.kr.lotteon.service.cs.CsService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ReviewService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CsService csService;

    public PageResponseDTO myReview(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("revNo");
        log.info("myReview...1");

        Page<ReviewEntity> entities =
                reviewRepository.findByUid(memberRepository
                        .findById(csService.loginStatus()).orElse(null), pageable);
        log.info("myReview...2 entities : " + entities);

        int total = (int) entities.getTotalElements();
        log.info("myReview...3 total : " + total);

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .reviewList(entities.stream()
                        .map(entity -> modelMapper.map(entity, ReviewDTO.class))
                        .collect(Collectors.toList()))
                .total(total)
                .build();
    }
}