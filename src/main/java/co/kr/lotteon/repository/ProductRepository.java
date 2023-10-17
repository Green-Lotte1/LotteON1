package co.kr.lotteon.repository;

import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    //////////////////////////////
    ////////    product list 출력
    //////////////////////////////
    public Page<ProductEntity> findByProdCate1AndProdCate2(ProdCate1Entity cate1, int cate2, Pageable pageable);
    public Page<ProductEntity> findByProdCate1AndProdCate2OrderBySoldDesc(ProdCate1Entity cate1, int cate2, Pageable pageable);
    public Page<ProductEntity> findByProdCate1AndProdCate2OrderByPriceAsc(ProdCate1Entity cate1, int cate2, Pageable pageable);
    public Page<ProductEntity> findByProdCate1AndProdCate2OrderByPriceDesc(ProdCate1Entity cate1, int cate2, Pageable pageable);
    public Page<ProductEntity> findByProdCate1AndProdCate2OrderByScoreDesc(ProdCate1Entity cate1, int cate2, Pageable pageable);
    public Page<ProductEntity> findByProdCate1AndProdCate2OrderByReviewDesc(ProdCate1Entity cate1, int cate2, Pageable pageable);
    public Page<ProductEntity> findByProdCate1AndProdCate2OrderByRdateAsc(ProdCate1Entity cate1, int cate2, Pageable pageable);

    //////////////////////////////
    ////////    product view 출력
    //////////////////////////////

    /*public ProductEntity findByProdNo();*/

    // return 배포 할 때 / 넣기로

}
