package co.kr.lotteon.repository.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsCate1Repository extends JpaRepository<CsCate1Entity, String> {

    public List<CsCate1Entity> findByGroup(CsGroupEntity group);

    public CsCate1Entity findByCate1(String cate1);
}