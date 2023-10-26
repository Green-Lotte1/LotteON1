package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodCate1")
    private ProdCate1Entity prodCate1;
    private int prodCate2;
    private String prodName;
    private String descript;
    private String prodCompany;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller")
    private MemberEntity seller;
    private int price;
    private int discount;
    private int point;
    private int stock;
    private int sold;
    private int delivery;
    private int hit;
    private int score;
    private int review;
    private String thumb1;
    private String thumb2;
    private String thumb3;
    private String detail;
    private String status;
    private String duty;
    private String receipt;
    private String bizType;
    private String origin;
    private String ip;
    @CreationTimestamp
    private LocalDateTime rdate;
    private int sale;
    private int etc2;
    private String etc3;
    private String etc4;
    private String etc5;


    public ProductDTO toDTO() {
        return ProductDTO.builder()
                .prodNo(prodNo)
                .prodName(prodName)
                .prodCate1(prodCate1.toDTO())
                .prodCate2(prodCate2)
                .descript(descript)
                .prodCompany(prodCompany)
                .seller(seller.toDTO())
                .price(price)
                .discount(discount)
                .point(point)
                .stock(stock)
                .sold(sold)
                .delivery(delivery)
                .hit(hit)
                .score(score)
                .review(review)
                .thumb1(thumb1)
                .thumb2(thumb2)
                .thumb3(thumb3)
                .detail(detail)
                .status(status)
                .duty(duty)
                .receipt(receipt)
                .bizType(bizType)
                .origin(origin)
                .ip(ip)
                .rdate(rdate)
                .sale(sale)
                .etc2(etc2)
                .etc3(etc3)
                .etc4(etc4)
                .etc5(etc5)
                .build();
    }

    public int discountingPrice(){
        return price - ((price/100)*discount);
    }

}
