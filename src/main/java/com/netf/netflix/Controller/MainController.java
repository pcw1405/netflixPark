package com.netf.netflix.controller;

import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {


    @GetMapping("/")
    public String getProfile(Model model, HttpSession session) {
        // 세션에서 nowUser 객체를 가져옴
        User nowUser = (User) session.getAttribute("nowUser");
        MemberDto nowMember = (MemberDto) session.getAttribute("nowMember");
        // 모델에 nowUser 객체를 추가하여 뷰로 전달
        model.addAttribute("nowUser", nowUser);
        model.addAttribute("nowMember", nowMember);

        String now_img=nowUser.getImg_url();
        System.out.println("현재 유저의 프로파일 경로"+now_img);
        System.out.println("현재 멤버목록= "+nowMember.getUsers());
        return "home";

    }
    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping("/user")
    public String user(){
        return "user-profile/user";
    }

//    @GetMapping("/register")
//    public String register(){
//        return "/register";
//    }

    @GetMapping("/myList")
    public String myList(){
        return "leftmain/myList";
    }

    @GetMapping("/movies")
    public String movies(){
        return "leftmain/movies";
    }

    @GetMapping("/drama")
    public String drama(){
        return "leftmain/drama";
    }

    @GetMapping("/profile-img")
    public String profile(){
        return "/profile-img";
    }
//    @GetMapping("/profile-img")
//    public String profile_img(){
//        return "/profile-img";
//    }
}
