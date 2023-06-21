package com.netf.netflix.Config;

import com.netf.netflix.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileInfoFilter extends GenericFilterBean {
    @Autowired
    private MemberService memberService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        //인증된 세션이 존재할경우 로그인된 사용자가 존재할경우
        //인증된세션에 프로필 이미지와 프로필 이름을 저장
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 프로필 정보를 세션에 저장
            httpRequest.getSession().setAttribute("profileImageUrl", "이미지 URL");
            httpRequest.getSession().setAttribute("profileName", "프로필 이름");
        }

        chain.doFilter(request, response);
    }
}