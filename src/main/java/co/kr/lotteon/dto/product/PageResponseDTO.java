package co.kr.lotteon.dto.product;

import co.kr.lotteon.dto.MemberDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PageResponseDTO {

    private List<ProductDTO> dtoList;
    private List<MemberDTO> memberList;
    private int cate1;
    private int cate2;
    private int pg;
    private int size;
    private int total;
    private String type;

    private int start, end;
    private boolean prev, next;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<ProductDTO> dtoList, List<MemberDTO> memberList,int total){
        this.cate1 = pageRequestDTO.getProdCate1();
        this.cate2 = pageRequestDTO.getProdCate2();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.type = pageRequestDTO.getType();
        this.total = total;
        this.dtoList = dtoList;
        this.memberList = memberList;

        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int last = (int) (Math.ceil(total / (double) size));

        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
