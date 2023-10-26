package co.kr.lotteon.dto.admin.cs;

import co.kr.lotteon.dto.coupon.CouponDTO;
import co.kr.lotteon.dto.coupon.MemberCouponDTO;
import co.kr.lotteon.dto.cs.CsDTO;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Data
public class PageResponseDTO {

    private int no;

    private String group;
    private String cate1;
    private String cate2;

    private List<CsDTO> csList;
    private List<CouponDTO> myCoupon;

    private int pg;
    private int size;
    private int total;

    private int start, end;
    private boolean prev, next;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<CsDTO> csList, List<CouponDTO> myCoupon, int total, int no) {

        this.group  = pageRequestDTO.getGroup();
        this.cate1  = pageRequestDTO.getCate1();
        this.cate2  = pageRequestDTO.getCate2();

        this.pg     = pageRequestDTO.getPg();
        this.size   = pageRequestDTO.getSize();
        this.total  = total;
        this.no     = no;

        this.csList = csList;
        this.myCoupon = myCoupon;

        this.end   = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int  last  = (int) (Math.ceil(total / (double) size));

        this.end  = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
