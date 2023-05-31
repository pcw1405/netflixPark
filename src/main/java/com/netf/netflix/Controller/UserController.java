package com.netf.netflix.Controller;

import com.netf.netflix.Dto.MemberDto;

import com.netf.netflix.Service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private final MemberService memberService;


    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("memberDto") @Valid MemberDto memberDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/register";
        }

        memberService.registerMember(memberDto);

        return "redirect:/login";
    }
}

