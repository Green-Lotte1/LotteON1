package co.kr.lotteon.dto.cs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsDTO {

    private int no;
    private int parent;
    private int group;
    private int cate1;
    private int cate2;
    private String uid;
    private String title;
    private String content;
    private LocalDateTime rdate;
}