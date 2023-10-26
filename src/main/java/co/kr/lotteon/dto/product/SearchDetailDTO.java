package co.kr.lotteon.dto.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class SearchDetailDTO {

    private String keyword;
    private Boolean byName;
    private Boolean byDescript;
    private Boolean byPrice;
    private int min;
    private int max;
}
