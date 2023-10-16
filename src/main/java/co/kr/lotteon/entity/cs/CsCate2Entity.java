package co.kr.lotteon.entity.cs;

import co.kr.lotteon.dto.cs.CsCate2DTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_board_cate2")
public class CsCate2Entity {

    @Id
    private String cate2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate1")
    private CsCate1Entity cate1;

    private String cate2_name;

    public CsCate2DTO toDTO() {
        return CsCate2DTO.builder()
                .cate2(cate2)
                .cate1(cate1.toDTO())
                .cate2_name(cate2_name)
                .build();
    }
}