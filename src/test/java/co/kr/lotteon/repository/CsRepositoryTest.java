package co.kr.lotteon.repository;

import co.kr.lotteon.dto.cs.PageRequestDTO;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.repository.cs.CsRepository;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class CsRepositoryTest {


    @Autowired
    private CsRepository csRepository;


    @Transactional
    public void findByMyQna(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<CsEntity> result =  csRepository.findByMyQna("b12345", pageable);
        List<CsEntity> list = result.getContent();
        System.out.println(list);
    }
}
