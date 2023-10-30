package co.kr.lotteon.dto.my;

import co.kr.lotteon.dto.member.PointDTO;
import co.kr.lotteon.dto.product.OrderDTO;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Data
public class PageResponseMyDTO {

    private String uid;
    private String type1;
    private String type2;

    List<PointDTO> list;
    List<OrderDTO> ordList;

    private int pg;
    private int size;
    private int total;

    private int start, end;
    private boolean prev, next;

    @Builder
    public PageResponseMyDTO(PageRequestMyDTO pageRequestMyDTO, List<PointDTO> list, List<OrderDTO> ordList, int total) {

        this.type1 = pageRequestMyDTO.getType1();
        this.type2 = pageRequestMyDTO.getType2();

        this.pg    = pageRequestMyDTO.getPg();
        this.size  = pageRequestMyDTO.getSize();
        this.total = total;

        this.list  = list;
        this.ordList = ordList;

        this.end   = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int  last  = (int) (Math.ceil(total / (double) size));

        this.end   = end > last ? last : end;
        this.prev  = this.start > 1;
        this.next  = total > this.end * this.size;
    }
}