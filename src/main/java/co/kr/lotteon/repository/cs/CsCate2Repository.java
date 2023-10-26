package co.kr.lotteon.repository.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsCate2Repository extends JpaRepository<CsCate2Entity, String> {

    public List<CsCate2Entity> findByCate1(CsCate1Entity cate1);

}
