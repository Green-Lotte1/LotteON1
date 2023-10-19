package co.kr.lotteon.repository.member;

import co.kr.lotteon.entity.member.TermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsRepository extends JpaRepository<TermsEntity, Integer> {
}
