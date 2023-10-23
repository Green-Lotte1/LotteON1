package co.kr.lotteon.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String uid = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        MyUserDetails findMember = (MyUserDetails) securityUserService.loadUserByUsername(uid);

        if (passwordEncoder.matches(password, findMember.getPassword())) {
            findMember.getMember().setPass(""); // password는 SecurityContextHolder(세션)에 돌아다니지 않게 한다.
            return new UsernamePasswordAuthenticationToken(uid, password, findMember.getAuthorities());
        }else {
            //TODO: thymeleaf 메시지로 어떻게 내보낼 것인지.
            throw new BadCredentialsException("비밀번호가 일치 하지 않는군요!");
        }
    }

    /**
     * Authentication클래스로 적용가능한 녀석인지 확인
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}