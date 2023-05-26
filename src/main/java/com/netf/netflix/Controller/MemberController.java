package com.netf.netflix.Controller;

import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MemberController {
    private MemberService memberService;

    @GetMapping("/login")
    public String loginMember(){
        return "/loginForm";
    }


}
