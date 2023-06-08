package com.netf.netflix.Controller;

import com.netf.netflix.Dto.MemberFormDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value="/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "/register";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/register";
        }try{
            Member member =Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "/register";
        }
        return "redirect:/members/login";
    }
    @GetMapping(value ="/login")
    public String loginMember(){
        return "/login";
    }
    @GetMapping(value="/login/error")
    public String loginError(Model model){
        model.addAttribute("loginEroorMsg","아이디 또는 비밀번호 확인해주세요.");
        return "/login";
    }

    @PostMapping(value="/login")
    public String loginMember(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto, BindingResult bindingResult, Model model, HttpSession session) {
        // 이메일과 비밀번호를 가져옴
        String email = memberFormDto.getEmail();
        String password = memberFormDto.getPassword();

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // 인증 성공 시
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 세션에 사용자 정보 저장
            session.setAttribute("loggedInUser", email);

            return "redirect:/profile/profile"; // 로그인 후 이동할 페이지 설정
        } catch (AuthenticationException e) {
            // 인증 실패 시
            model.addAttribute("loginError", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "/login";
        }

    }





}
