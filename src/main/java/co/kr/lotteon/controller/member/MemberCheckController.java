package co.kr.lotteon.controller.member;

import co.kr.lotteon.service.member.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/member/check")
public class MemberCheckController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/uid/{uid}")
    public int checkUid(@PathVariable("uid") String uid){
        int result = memberService.countUid(uid);
        log.info("checkUid : "+result);
        return result;
    }
    @GetMapping("/email/{email}")
    public int checkEmail(@PathVariable("email") String email){
        int result = memberService.countEmail(email);
        log.info("checkEmail : "+result);
        return result;
    }
    @GetMapping("/hp/{hp}")
    public int checkHp(@PathVariable("hp") String hp){
        int result = memberService.countHp(hp);
        log.info("checkHp : "+result);
        return result;
    }
}
