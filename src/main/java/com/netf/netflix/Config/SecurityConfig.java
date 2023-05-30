//package com.netf.netflix.Config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/register").permitAll() // 회원가입 페이지는 인증 없이 접근 가능하도록 설정
//                .anyRequest().authenticated() // 나머지 요청은 인증 필요
//                .and()
//                .formLogin()
//                .loginPage("/login") // 로그인 페이지 경로 설정
//                .defaultSuccessUrl("/home") // 로그인 성공 후 이동할 페이지 설정
//                .and()
//                .logout()
//                .logoutUrl("/logout") // 로그아웃 경로 설정
//                .logoutSuccessUrl("/login"); // 로그아웃 후 이동할 페이지 설정
//    }
//}
