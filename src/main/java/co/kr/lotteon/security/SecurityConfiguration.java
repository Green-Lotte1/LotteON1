package co.kr.lotteon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfiguration {
    @Autowired
    private SecurityUserService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http    
                // 개발전용 - 에러 페이지 띄우기
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/error/403"); // 403 Forbidden 에러 페이지로 리디렉트
                })
                .and()
                // 사이트 위변조 방지 비활성
                .csrf(CsrfConfigurer::disable) // 메서드 참조 연산자로 람다식을 간결하게 표현
                // 토큰방식으로 로그인처리하기 때문에 폼방식 비활성
                .formLogin(config -> config.loginPage("/member/login")
                        .defaultSuccessUrl("/",true) // 첫방문도 가능하게 해줌
                        .failureUrl("/member/login?success=100")
                        .usernameParameter("uid")
                        .passwordParameter("pass")
                        .permitAll())
                // 로그아웃 설정
                .logout(config -> config
                        .logoutUrl("/member/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/member/login?success=200"))
                // 인가 권한 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/admin/**").permitAll()
                        .requestMatchers("/cs/**").permitAll()
                        .requestMatchers("/member/**").permitAll()
                        .requestMatchers("/product/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll())
                // 403 Forbidden 에러 처리
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN))
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 가장 강한 비밀번호 암호화
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
