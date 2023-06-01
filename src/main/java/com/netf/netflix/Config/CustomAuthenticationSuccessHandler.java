package com.netf.netflix.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final HttpSession session;

    public CustomAuthenticationSuccessHandler(HttpSession session) {
        this.session = session;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        // 인증 정보에서 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 세션에 사용자 정보 저장
        session.setAttribute("userDetails", userDetails);

        // 로그인 성공 후 이동할 페이지로 리다이렉트
        response.sendRedirect("/");
    }
}
