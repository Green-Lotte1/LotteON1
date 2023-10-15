package co.kr.lotteon.repository.cs;

import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsRepository extends JpaRepository<CsEntity, Integer> {


    ////////////////////////////////////
    // list page
    ////////////////////////////////////
    // group 게시글 출력 (notice)
    public Page<CsEntity> findByGroupAndParent(CsGroupEntity group, int parent, Pageable pageable);


    // cate1 게시글 출력 (notice, qna, faq)
    public Page<CsEntity> findByCate1AndParent(String cate1, int parent, Pageable pageable);

    // cate2 게시글 출력 (faq)
    //public Page<CsEntity> findByGroupAndCate1AndCate2AndParent(String group, int cate1, int cate2, int parent, Pageable pageable);



    ////////////////////////////////////
    // view page
    ////////////////////////////////////




    ////////////////////////////////////
    // write page
    ////////////////////////////////////

}
