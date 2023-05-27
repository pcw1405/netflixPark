package com.netf.netflix.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model){
        return "home";
    }
    @GetMapping("/login")
    public String loginform(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping("/user")
    public String user(){
        return "user-profile/user";
    }

    @GetMapping("/register")
    public String register(){
        return "/register";
    }

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

    @GetMapping("/profile")
    public String profile(){
        return "/profile";
    }
}
