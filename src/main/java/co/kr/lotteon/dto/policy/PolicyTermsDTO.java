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
