package co.kr.lotteon.dto.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsGroupEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsCate1DTO {

    private CsGroupDTO group;
    private String cate1;
    private String cate1_name;
    private String cate1_discription;

    public CsCate1Entity toEntity() {
        return CsCate1Entity.builder()
                .group(group.toEntity())
                .cate1(cate1)
                .cate1_name(cate1_name)
                .cate1_discription(cate1_discription)
                .build();
    }
}