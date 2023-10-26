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

    public String getEscapeContent() {
        return content
                .replace("&",     "&amp;")
                .replace("<",     "&lt;")
                .replace(">",     "&gt;")
                .replace("\"",    "&quot;")
                .replace("'",     "&#39;")
                .replace(" "  ,   "&nbsp;")
                .replace("  ",    "&Tab;")
                .replace("\n",    "<br/>")
                .replace("\r\n",  "<br/>");
    }

}
