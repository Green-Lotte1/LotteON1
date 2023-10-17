package co.kr.lotteon.dto.cs;

import co.kr.lotteon.dto.MemberDTO;
import co.kr.lotteon.entity.cs.CsEntity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
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

    private int no;
    private int parent;
    private CsGroupDTO group;
    private CsCate1DTO cate1;
    private CsCate2DTO cate2;
    private MemberDTO uid;
    private String title;
    private String content;
    private LocalDateTime rdate;

    public String getYyMMdd() {
        return rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }

    public String getYyyyMMdd() {
        return rdate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public String getYyyyMMdd2() {
        return rdate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public CsEntity toEntity() {
        return CsEntity.builder()
                .no(no)
                .parent(parent)
                .group(group.toEntity())
                .cate1(cate1.toEntity())
                .cate2(cate2.toEntity())
                .uid(uid.toEntity())
                .title(title)
                .content(content)
                .rdate(rdate)
                .build();
    }
}