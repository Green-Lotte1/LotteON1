package co.kr.lotteon.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "km_member_point")
public class PointEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int pointNo;
    private String uid;
    private int ordNo;
    private int point;
    private LocalDateTime pointDate;

}
