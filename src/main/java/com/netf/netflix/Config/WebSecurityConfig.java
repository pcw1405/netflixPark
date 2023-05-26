package com.netf.netflix.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("email") // 사용자 이름
                .password("{noop}password") // 비밀번호 (noop은 암호화를 사용하지 않음을 나타냄)
                .roles("USER"); // 사용자 역할
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll() // 로그인 페이지는 모든 사용자에게 허용
                .anyRequest().authenticated() // 다른 모든 요청은 인증이 필요
                .and()
                .formLogin()
                .loginPage("/login") // 로그인 페이지의 경로
                .defaultSuccessUrl("/home") // 로그인 성공 후 이동할 경로
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}