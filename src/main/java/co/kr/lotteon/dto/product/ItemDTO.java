package co.kr.lotteon.dto.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class ItemDTO {

    private int prodNo;
    private String prodName;
    private String descript;
    private int price;
    private int discount;
    private int point;
    private int delivery;
    private int total;
    private int prodCate1;
    private int prodCate2;
    private int count;
    private String thumb1;


    public int savePoint(){
        return ((discountingPrice()*count)/100)*point;
    }

    public int discountingPrice(){
        return price - ((price/100)*discount);
    }

    public int totalPrice(){
        return (discountingPrice()*count)+delivery;
    }

}
