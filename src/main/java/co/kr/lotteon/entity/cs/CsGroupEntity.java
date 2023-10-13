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
@Entity
@Table(name = "km_board_group")
public class CsGroupEntity {


    private int group;
    private String group_name;
}