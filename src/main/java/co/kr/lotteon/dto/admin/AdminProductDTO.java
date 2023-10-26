package co.kr.lotteon.dto.admin;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.product.ProdCate1DTO;
import co.kr.lotteon.entity.product.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminProductDTO {

    private int prodNo;
    private int prodCate1;
    private int prodCate2;
    private String prodName;
    private String descript;
    private String prodCompany;
    private String seller;
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

    public int discountingPrice(){
        return price - ((price/100)*discount);
    }
}
