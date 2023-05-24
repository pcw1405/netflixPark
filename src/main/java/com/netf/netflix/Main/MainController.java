package com.netf.netflix.Main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(){
        return "home";
    }
    @GetMapping("/login")
    public String loginform(){
        return "login";
    }
}
