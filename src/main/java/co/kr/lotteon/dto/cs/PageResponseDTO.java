package co.kr.lotteon.dto.cs;

import co.kr.lotteon.entity.cs.CsCate1Entity;
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
    private String cate2; // cate2는 페이징에 필요 없으나 일단 선언해둠.

    private List<CsDTO> csList;
    private List<List<CsDTO>> csLists;

    private int pg;
    private int size;
    private int total;

    private int start, end;
    private boolean prev, next;

    private String type;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<CsDTO> csList, List<List<CsDTO>> csLists, int total, int no) {

        this.no = no;

        this.group  = pageRequestDTO.getGroup();
        this.cate1  = pageRequestDTO.getCate1();
        this.pg  = pageRequestDTO.getPg();
        this.size   = pageRequestDTO.getSize();
        this.total  = total;

        this.csList = csList;
        this.csLists = csLists;

        this.type = pageRequestDTO.getType();

        this.end   = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;
        int last   = (int) (Math.ceil(total / (double) size));

        this.end  = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}
