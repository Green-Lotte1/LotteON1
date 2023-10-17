package co.kr.lotteon.entity.cs;

import co.kr.lotteon.dto.cs.CsCate1DTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_board_cate1")
public class CsCate1Entity {

    @Id
    private String cate1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`group`")
    private CsGroupEntity group;

    private String cate1_name;
    private String cate1_discription;

    public CsCate1DTO toDTO() {
        return CsCate1DTO.builder()
                .cate1(cate1)
                .group(group.toDTO())
                .cate1_name(cate1_name)
                .cate1_discription(cate1_discription)
                .build();
    }
}