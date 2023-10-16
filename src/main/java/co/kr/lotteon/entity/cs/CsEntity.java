package co.kr.lotteon.entity.cs;

import co.kr.lotteon.dto.cs.CsDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group")
    private CsGroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate1")
    private CsCate1Entity cate1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate2")
    private CsCate2Entity cate2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private MemberEntity uid;

    private String title;
    private String content;

    @CreationTimestamp
    private LocalDateTime rdate;

    public CsDTO toDTO() {
        return CsDTO.builder()
                .no(no)
                .parent(parent)
                .group(group.toDTO())
                .cate1(cate1.toDTO())
                .cate2(cate2.toDTO())
                .uid(uid.toDTO())
                .title(title)
                .content(content)
                .rdate(rdate)
                .build();
    }
}