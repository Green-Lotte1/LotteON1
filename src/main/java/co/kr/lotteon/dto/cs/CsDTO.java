package co.kr.lotteon.dto.cs;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.dto.product.ProductDTO;
import co.kr.lotteon.entity.cs.CsEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsDTO {

    private    int             no;
    private    int             parent;
    private    int             comment;
    private    CsGroupDTO      group;
    private    CsCate1DTO      cate1;
    private    CsCate2DTO      cate2;
    private    MemberDTO       uid;
    private    String          title;
    private    String          content;
    private    int             hit;
    private    ProductDTO      prodNo;
    private    String          email;
    private    LocalDateTime   rdate;

    // 추가필드
    private    String          answer;
    private    LocalDateTime   ansdate;

    public String getBrContent() {
        return content
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
                .replace(" ", "&nbsp;")
                .replace("  ", "&Tab;")
                .replace("\n", "<br/>")
                .replace("\r\n", "<br/>");
    }

    public String getBrTitle() {
        return title
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
                .replace(" ", "&nbsp;")
                .replace("  ", "&Tab;")
                .replace("\n", "<br/>")
                .replace("\r\n", "<br/>");
    }

    public String getYyMMdd() {
        return rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }

    public String getYyyyMMdd() {
        return rdate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public String getYyyyMMdd2() {
        return rdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getYyyyMMddHHmmss() {
        return rdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public String getYyyyMMddHHmmss2() {
        return ansdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public CsEntity toEntity() {
        CsEntity.CsEntityBuilder builder = CsEntity.builder()
                .no(no)
                .parent(parent)
                .comment(comment)
                .group(group.toEntity())
                .cate1(cate1.toEntity())
                .uid(uid.toEntity())
                .title(title)
                .content(content)
                .hit(hit)
                .email(email)
                .rdate(rdate);

        if (cate2 != null) {
            builder.cate2(cate2.toEntity());
        }
        if (prodNo != null) {
            builder.prodNo(prodNo.toEntity());
        }

        return builder.build();
    }
}