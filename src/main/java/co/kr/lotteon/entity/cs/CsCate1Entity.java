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
//@Table(name = "km_board_cate1")
public class CsCate1Entity {

    private int group;
    private int cate1;
    private String cate1_name;
    private String cate1_discription;
}