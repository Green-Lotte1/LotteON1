package co.kr.lotteon.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TermsDTO {

    private String terms;
    private String privacy;
    private String location;
    private String finance;
    private String tax;

}
