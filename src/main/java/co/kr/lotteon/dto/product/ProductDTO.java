package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.dto.Utils;
import co.kr.lotteon.entity.product.ProdCate1Entity;
import co.kr.lotteon.entity.product.ProductEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ProductDTO {

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


}
