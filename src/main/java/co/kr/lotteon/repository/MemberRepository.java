package co.kr.lotteon.repository;

import co.kr.lotteon.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {

    public int countByEmail(String email);
    public int countByHp(String hp);

}
