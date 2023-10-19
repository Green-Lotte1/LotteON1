package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.product.ReviewEntity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ReviewDTO {

    @Id
    private int revNo;
    private ProductDTO prodNo;
    private String content;
    private MemberDTO uid;
    private int rating;
    private String regip;
    private LocalDateTime rdate;

    public String getYyyyMMdd2() {
        return rdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public ReviewEntity toEntity(){
        return ReviewEntity.builder()
                .revNo(revNo)
                .prodNo(prodNo.toEntity())
                .content(content)
                .uid(uid.toEntity())
                .rating(rating)
                .regip(regip)
                .rdate(rdate)
                .build();
    }
}
