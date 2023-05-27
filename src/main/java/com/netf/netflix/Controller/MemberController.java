package com.netf.netflix.Controller;

import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("member/login")
    public String loginform(){
        return "login";
    }

    @PostMapping("member/login")
    public String loginFormMember(@ModelAttribute MemberDto memberDto, HttpSession session){


            MemberDto loginConfirm = memberService.login(memberDto);
            if(loginConfirm!=null){
                session.setAttribute("loginConfirm",loginConfirm.getEmail());
                return "login";
//                로그인 성공시 임시로 home으로 가게 설정
            } else{
                return "login";
//                로그인 실패시 다시 로그인화면으로
            }
    }


}
