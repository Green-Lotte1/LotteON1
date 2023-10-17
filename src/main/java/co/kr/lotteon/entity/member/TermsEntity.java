package co.kr.lotteon.entity.member;

import co.kr.lotteon.dto.member.TermsDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "km_member_terms")
public class TermsEntity {

    @Id
    private int tno;
    private String terms;
    private String privacy;
    private String location;
    private String finance;
    private String tax;

    public TermsDTO toDTO(){
        return TermsDTO.builder()
                .tno(tno)
                .terms(terms)
                .privacy(privacy)
                .location(location)
                .finance(finance)
                .tax(tax)
                .build();
    }
}
