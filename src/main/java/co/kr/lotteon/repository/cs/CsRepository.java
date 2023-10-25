package co.kr.lotteon.repository.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsRepository extends JpaRepository<CsEntity, Integer> {


    ////////////////////////////////////
    // list page
    ////////////////////////////////////
    // group 게시글 출력 (notice)
    public Page<CsEntity> findByGroupAndParent(CsGroupEntity group, int parent, Pageable pageable);

    // cate1 게시글 출력 (notice, qna, faq)
    public Page<CsEntity> findByCate1AndParent(CsCate1Entity cate1, int parent, Pageable pageable);

    // cate2 게시글 출력 (faq)
    public Page<CsEntity> findByGroupAndCate1AndCate2AndParent(CsGroupEntity group, CsCate1Entity cate1, CsCate2Entity cate2, int parent, Pageable pageable);
    public Page<CsEntity> findByCate2AndParent(CsCate2Entity cate2, int parent, Pageable pageable);

    // 답변 게시글 출력
    public CsEntity findByParent(int no);

    // 내 질문글 리스트 출력
    @Query(value = "SELECT * FROM km_board WHERE (`group` = 'qna' OR `group` = 'seller') AND uid = ?1",
        countQuery = "SELECT count(*) FROM km_board WHERE (`group` = 'qna' OR `group` = 'seller') AND uid = ?1",
        nativeQuery = true)
    public Page<CsEntity> findByMyQna(@Param("uid") String uid, Pageable pageable);



    ////////////////////////////////////
    // etc page
    ////////////////////////////////////
    public int countByNo(int no);

}
