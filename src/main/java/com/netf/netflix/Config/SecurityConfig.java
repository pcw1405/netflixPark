package com.netf.netflix.Config;


import com.netf.netflix.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


    @Autowired
   MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login") //로그인 페이지 url 설정
                .defaultSuccessUrl("/profile") // 성공시 이동할 url
                .usernameParameter("email") //로그인시 사용할 파라미터 이름으로 email 지정
                .failureUrl("/error/login") //.로그인 실패시 이동할 url
                .and()
                .logout() //로그아웃 url
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //패턴과 요청url을 비교하여 일치하는지 판단
                //'members/logout' url 로 요청을 보내면 이를 로그아웃 처리로 인식 하여 로그아웃
                .logoutSuccessUrl("/") //로그아웃 성공시 이동할 url
        ;
        http.authorizeRequests() //시큐리티 처리에 HttpServletRequest를 이용한다는 의미
                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll() //모든사용자가 로그인 없이 경로 접근
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**", "/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")  //ADMIN Role일 경우 접근가능
                .anyRequest().authenticated()
        ;
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ; //인증되지 않은 사용자가 리소스에 접근했을때 수행되는 핸들러 등록

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //PasswordEncoder 인테페이스 구현체 BCryptPasswordEncoder();
    }
}
