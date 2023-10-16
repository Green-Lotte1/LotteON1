package co.kr.lotteon.service;

import co.kr.lotteon.repository.MemberRepository;
import co.kr.lotteon.repository.ProductRepository;
import co.kr.lotteon.repository.cs.CsRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Log4j2
@Builder
@RequiredArgsConstructor
@Service
public class AdminService {

    private final ModelMapper modelMapper;
    private final CsRepository csRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    }









