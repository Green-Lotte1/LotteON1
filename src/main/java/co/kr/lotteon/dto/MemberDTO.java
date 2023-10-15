package co.kr.lotteon.dto;

import co.kr.lotteon.entity.MemberEntity;
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

    private String km_uid;
    private String km_pass1;
    private String km_name;
    private int km_gender;
    private String km_hp;
    private String km_email;
    private int type;
    private int point;
    private int level;
    private String km_zip;
    private String km_addr1;
    private String km_addr2;
    private String kms_company;
    private String kms_ceo;
    private String kms_corp_reg;
    private String kms_online_reg;
    private String kms_tel;
    private String kms_manager;
    private String kms_managerHp;
    private String kms_fax;
    private String regip;
    private LocalDateTime wdate;
    private LocalDateTime rdate;
    private int location;
    private int etc2;
    private String etc3;
    private String etc4;
    private String etc5;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .uid(km_uid)
                .pass(km_pass1)
                .name(km_name)
                .gender(km_gender)
                .type(type)
                .point(point)
                .zip(km_zip)
                .addr1(km_addr1)
                .addr2(km_addr2)
                .company(kms_company)
                .bizRegNum(kms_corp_reg)
                .comRegNum(kms_online_reg)
                .tel(kms_tel)
                .manager(kms_manager)
                .managerHp(kms_managerHp)
                .location(location)
                .etc2(etc2)
                .etc3(etc3)
                .etc4(etc4)
                .etc5(etc5)
                .build();
    }
}
