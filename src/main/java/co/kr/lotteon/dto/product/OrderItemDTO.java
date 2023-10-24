package co.kr.lotteon.dto.product;

import jakarta.persistence.Id;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class OrderItemDTO {

    @Id
    private int no;
    private int ordNo;
    private int prodNo;
    private int count;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;


    public int discounting(){
        return ((price*count) / 100) * discount;
    }
}
