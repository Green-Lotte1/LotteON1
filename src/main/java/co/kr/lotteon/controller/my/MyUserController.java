package co.kr.lotteon.controller.my;

import co.kr.lotteon.dto.member.MemberDTO;
import co.kr.lotteon.entity.member.MemberEntity;
import co.kr.lotteon.security.MyUserDetails;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class MyUserController {
    @ResponseBody
    @GetMapping("/my/getUser")
    public MemberEntity userData(){
        MemberEntity memberEntity = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails instanceof MyUserDetails) {
                MyUserDetails myUserDetails = (MyUserDetails) userDetails;
                memberEntity = myUserDetails.getMember();

            }
        }
        return memberEntity;
    }

}
