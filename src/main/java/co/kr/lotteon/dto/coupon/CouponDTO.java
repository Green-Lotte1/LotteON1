package co.kr.lotteon.dto.coupon;

import co.kr.lotteon.dto.Utils;
import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.coupon.CouponEntity;
import co.kr.lotteon.entity.member.MemberEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CouponDTO {

    private     int              coupNo;

    private     MemberDTO        uid;
    private     String           coupName;
    private     int              type;
    private     int              coupDiscount;
    private     int              coupLimit;
    private     int              coupStock;
    private     int              coupPeriod;
    
    private     LocalDate        rdate;
    private     LocalDate        wdate;


    // 추가 필드
    private     int              ordItemNo;
    private     LocalDate        exp;


    public String getLimitWithComma() {
        return Utils.comma(coupLimit);
    }

    public String getDiscountWithComma() {
        return Utils.comma(coupDiscount);
    }

    public String getExpWithWave() {
        return expWithWave(exp);
    }
    public String getResult() {
        return dateComparison(exp, ordItemNo);
    }






    public String expWithWave(LocalDate exp) {
        return "~" + (""+exp).substring(5).replace("-", "/");
    }

    public String dateComparison(LocalDate exp, int ordItemNo) {
        LocalDate today = LocalDate.now();

        String result = null;

        if((exp.equals(today) || exp.isAfter(today)) && ordItemNo == 0) {
            result = "사용가능";

        }else if(ordItemNo != 0) {
            result = "사용완료";

        }else if(exp.isBefore(today) && ordItemNo == 0) {
            result = "기간만료";
        }
        return result;
    }
}