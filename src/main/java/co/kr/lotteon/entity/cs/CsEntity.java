package co.kr.lotteon.entity.cs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsEntity {
    private int no;
    private int parent;
    private int group;
    private int cate1;
    private int cate2;


    private String title;
    private String content;

    private LocalDateTime rdate;
}