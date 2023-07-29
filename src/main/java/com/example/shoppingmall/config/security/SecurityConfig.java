package com.example.shoppingmall.config.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/status", "/images/**", "/","/member/join","/member/login").permitAll() //인증 제외
                        .anyRequest().authenticated() //어떠한 요청이라도 인증필요
                )
                .formLogin(login -> login //form 방식 로그인 사용
                        .loginPage("/member/login") //커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/member/login") //submit 받을 url
                        .usernameParameter("memberId") //submit 할 아이디
                        .passwordParameter("memberPw") //submit 할 비밀번호
                        .defaultSuccessUrl("/loginHome", true) //성공 시 home으로
                        .permitAll() //home 이동이 막히면 안되므로 얘는 허용
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) //로그아웃 URL 설정
                        .logoutSuccessUrl("/") //로그아웃 성공 시
                        .invalidateHttpSession(true));//생성된 사용자 세션 삭제

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
