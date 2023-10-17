package co.kr.lotteon.dto.member;

import co.kr.lotteon.entity.member.TermsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TermsDTO {

    private int tno;
    private String terms;
    private String privacy;
    private String location;
    private String finance;
    private String tax;

    public TermsEntity toEntity(){
        return TermsEntity.builder()
                .tno(tno)
                .terms(terms)
                .privacy(privacy)
                .location(location)
                .finance(finance)
                .tax(tax)
                .build();
    }
}
