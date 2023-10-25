package co.kr.lotteon.dto.member;

import co.kr.lotteon.entity.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

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
    private LocalDateTime rdate;
    private int location;
    private String ssn;
    private String etc3;
    private String etc4;
    private String etc5;

    public String getUidMasking(){
        return uid.replaceAll("(?<=.{3})." , "*");
    }
    /*public String getSsnMasking(){
        return ssn.replaceAll("(?<=.{3})." , "*");
    }*/

    public MemberEntity toEntity(){
        return MemberEntity.builder()
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
                .ssn(ssn)
                .etc3(etc3)
                .etc4(etc4)
                .etc5(etc5)
                .build();
    }
}
