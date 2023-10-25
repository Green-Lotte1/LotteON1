package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.product.ProductEntity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ProductDTO {

    @Id
    private int prodNo;
    private ProdCate1DTO prodCate1;
    private int prodCate2;
    private String prodName;
    private String descript;
    private String prodCompany;
    private MemberDTO seller;
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
    private MultipartFile fileThumb1;
    private MultipartFile fileThumb2;
    private MultipartFile fileThumb3;
    private MultipartFile fileDetail;
    private String status;
    private String duty;
    private String receipt;
    private String bizType;
    private String origin;
    private String ip;
    private LocalDateTime rdate;
    private int sale;
    private int etc2;
    private String etc3;
    private String etc4;
    private String etc5;


    public ProductEntity toEntity() {

        return ProductEntity.builder()
                .prodNo(prodNo)
                .prodCate1(prodCate1.toEntity())
                .prodCate2(prodCate2)
                .descript(descript)
                .prodCompany(prodCompany)
                .seller(seller.toEntity())
                .price(price)
                .discount(discount)
                .point(point)
                .stock(stock)
                .sold(sold)
                .delivery(delivery)
                .hit(hit)
                .score(score)
                .review(review)
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
