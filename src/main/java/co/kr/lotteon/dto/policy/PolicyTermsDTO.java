package co.kr.lotteon.dto.policy;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PolicyTermsDTO {

    private int tno;
    private String buyer;
    private String finance;
    private String location;
    private String privacy;
    private String seller;

    // 추가필드
    private String tit;
    private String content;


    public String getPolicyContent() {
        if(content != null){
            content = content
                 .replace(" ", "&nbsp;")
                 .replace("  ", "&Tab;")
                 .replace("\r\n", "<br/>")
                 .replace("\n", "<br/>");

        }
        return content;
    }

        /*public String[] getTermsArr() {
            return terms.split("\n\n");
        }
        public String[] getPrivacyArr() {
            return terms.split("\n\n");
        }
        public String[] getLocationArr() {
            return terms.split("\n\n");
        }
        public String[] getFinanceArr() {
            return terms.split("\n\n");
        }
        public String[] getSellerArr() {
            return terms.split("\n\n");
        }
*/
        /*public String[] splitPolicy(String signup) {
            String[] signArr = signup.split("\n\n");
            return signArr;
        }*/
}
