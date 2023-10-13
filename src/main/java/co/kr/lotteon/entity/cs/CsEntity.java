package co.kr.lotteon.entity.cs;

import co.kr.lotteon.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "km_board")
public class CsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private int parent;
    private int group;
    private int cate1;
    private int cate2;

    @ManyToOne
    @JoinColumn(name = "uid")
    private MemberEntity member;

    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime rdate;
}