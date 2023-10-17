package co.kr.lotteon.entity;

import co.kr.lotteon.dto.MemberDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "km_member")
public class MemberEntity {

    @Id
    private String uid;
    private String pass;
    private String name;
    private int gender;
    private String hp;
    private String email;
    private int type;
    private int point;
    private int level;
    private String zip;
    private String addr1;
    private String addr2;
    private String company;
    private String ceo;
    private String bizRegNum;
    private String comRegNum;
    private String tel;
    private String manager;
    private String managerHp;
    private String fax;
    private String regip;
    private LocalDateTime wdate;

    @CreationTimestamp
    private LocalDateTime rdate;
    private int location;
    private int etc2;
    private String etc3;
    private String etc4;
    private String etc5;

    public MemberDTO toDTO(){
        return MemberDTO.builder()
                .uid(uid)
                .pass(pass)
                .name(name)
                .gender(gender)
                .hp(hp)
                .email(email)
                .type(type)
                .point(point)
                .level(level)
                .zip(zip)
                .addr1(addr1)
                .addr2(addr2)
                .company(company)
                .ceo(ceo)
                .bizRegNum(bizRegNum)
                .comRegNum(comRegNum)
                .tel(tel)
                .manager(manager)
                .managerHp(managerHp)
                .fax(fax)
                .regip(regip)
                .wdate(wdate)
                .rdate(rdate)
                .location(location)
                .etc2(etc2)
                .etc3(etc3)
                .etc4(etc4)
                .etc5(etc5)
                .build();
    }
}
