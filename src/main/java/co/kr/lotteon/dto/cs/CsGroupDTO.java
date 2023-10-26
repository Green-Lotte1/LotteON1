package co.kr.lotteon.dto.cs;

import co.kr.lotteon.entity.cs.CsGroupEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CsGroupDTO {

    private String group;
    private String group_name;

    public CsGroupEntity toEntity() {
        return CsGroupEntity.builder()
                .group(group)
                .group_name(group_name)
                .build();
    }
}