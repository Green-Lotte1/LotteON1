package co.kr.lotteon.dto.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
import co.kr.lotteon.entity.cs.CsCate2Entity;
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
public class CsCate2DTO {


    private int no;
    private CsCate1DTO cate1;
    private int cate2;
    private String cate2_name;

    public CsCate2Entity toEntity() {
        return CsCate2Entity.builder()
                .no(no)
                .cate1(cate1.toEntity())
                .cate2(cate2)
                .cate2_name(cate2_name)
                .build();
    }
}