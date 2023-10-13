package co.kr.lotteon.dto.cs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsCate1DTO {

    private int group;
    private int cate1;
    private String cate1_name;
    private String cate1_discription;
}