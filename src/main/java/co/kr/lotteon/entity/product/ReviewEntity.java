package co.kr.lotteon.entity.product;

import co.kr.lotteon.dto.product.ReviewDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_product_review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int revNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prodNo")
    private ProductEntity prodNo;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private MemberEntity uid;
    private int rating;
    private String regip;
    @CreationTimestamp
    private LocalDateTime rdate;

    public ReviewDTO toDTO(){
        return ReviewDTO.builder()
                .revNo(revNo)
                .prodNo(prodNo.toDTO())
                .content(content)
                .uid(uid.toDTO())
                .rating(rating)
                .regip(regip)
                .rdate(rdate)
                .build();
    }
}
