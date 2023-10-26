package co.kr.lotteon.entity.cs;

import co.kr.lotteon.dto.cs.CsGroupDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    @Id
    private String group;
    private String group_name;

    public CsGroupDTO toDTO() {
        return CsGroupDTO.builder()
                .group(group)
                .group_name(group_name)
                .build();
    }
}