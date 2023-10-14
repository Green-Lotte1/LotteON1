package co.kr.lotteon.entity.cs;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
//@Entity
//@Table(name = "km_board_cate2")
public class CsCate2Entity {


    private int cate1;
    private int cate2;
    private String cate2_name;
}