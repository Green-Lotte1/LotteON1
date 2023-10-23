package co.kr.lotteon.dto.member;

import java.time.LocalDateTime;

public interface MemberInfoMapping {

    String uid();
    String name();
    int gender();
    String hp();
    String email();
    int type();
    int point();
    int level();
    String zip();
    String addr1();
    String addr2();
    String company();
    String ceo();
    String bizRegNum();
    String comRegNum();
    String tel();
    String manager();
    String managerHp();
    String fax();
    String regip();
    LocalDateTime wdate();
    LocalDateTime rdate();
    int location();
    int etc2();
    String etc3();
    String etc4();
    String etc5();
}
