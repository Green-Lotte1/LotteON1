package co.kr.lotteon.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class CategoryDTO {

    private int cate1No;
    private String c1Name;
    private int cate2No;
    private String c2Name;
    private String cate1Icon;
}
