package co.kr.lotteon.dto.cs;

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
    private String cate1;
    private int cate2;
    private String uid;
    private String title;
    private String content;
    private LocalDateTime rdate;

    public String getYyMMdd() {
        return rdate.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }

    public CsEntity toEntity() {
        return CsEntity.builder()
                .no(no)
                .parent(parent)
                .group(group.toEntity())
                .cate1(cate1)
                .cate2(cate2)
                .uid(uid)
                .title(title)
                .content(content)
                .rdate(rdate)
                .build();
    }
}