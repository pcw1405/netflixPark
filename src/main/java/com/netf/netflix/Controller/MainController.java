package com.netf.netflix.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.plaf.PanelUI;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main(){
        return "home";
    }

    @GetMapping(value = "/profile-new")
    public String login(){
        return "profile-new";
    }
}
