package co.kr.lotteon.repository.cs;

import co.kr.lotteon.entity.cs.CsCate2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsCate2Repository extends JpaRepository<CsCate2Entity, Integer> {

}
