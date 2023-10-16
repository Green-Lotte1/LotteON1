package co.kr.lotteon.repository.cs;

import co.kr.lotteon.entity.cs.CsGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsGroupRepository extends JpaRepository<CsGroupEntity, String> {

}
