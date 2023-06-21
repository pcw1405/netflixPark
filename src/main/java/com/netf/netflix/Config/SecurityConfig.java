package com.netf.netflix.Config;

import com.netf.netflix.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;


    //비밀번호 인코더 패스워드 디비 저장시 인코더로 인하여 관리자도 회원의 비밀번호를 알수없음 . 필수보안
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //css 적용파일 및 js img 파일 권한부여
                //permitall 전체권한 적용
                .mvcMatchers("/assets/**", "/assets/js/**", "/img/**").permitAll()
                .mvcMatchers("/","/members/**","/item/**", "/images/**").permitAll()
                .mvcMatchers("/register").permitAll()
                .mvcMatchers("/video/**").hasRole("ADMIN")
                .mvcMatchers("/profile").hasAnyRole("ADMIN","USER")
                .mvcMatchers("/kid").hasRole("KID")
                .antMatchers("/save-like").permitAll()
                .mvcMatchers("/profile/saveImageUrl").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile/profile")
                .usernameParameter("email")
                .failureUrl("/login/error") // 로그인 실패 시 이동할 URL
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                .and()
                //인가되지않은 사용자 접근 제한
                //CustomAuthenticationEntryPoint 상속받아 인가되지않은 또는 세션없는 사용자,권한외에 주소창 강제로 접속시 members/login 으로우회 시킴
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedPage("/access-denied");
        http
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

              // 프로필 정보를 세션에 저장하는 필터 추가
        http.addFilterBefore(new ProfileInfoFilter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}