package co.kr.lotteon.entity.cs;

import co.kr.lotteon.dto.cs.CsDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group")
    private CsGroupEntity group;

    private String cate1;
    private int cate2;

    // member table join은 merge후 진행.
    private String uid;
    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime rdate;

    public CsDTO toDTO() {
        return CsDTO.builder()
                .no(no)
                .parent(parent)
                .group(group.toDTO())
                .cate1(cate1)
                .cate2(cate2)
                .uid(uid)
                .title(title)
                .content(content)
                .rdate(rdate)
                .build();
    }
}