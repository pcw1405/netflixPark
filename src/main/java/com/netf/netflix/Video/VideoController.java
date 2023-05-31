package com.netf.netflix.Video;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoController {
    @GetMapping(value = "/video/new")
    public String videoForm(Model model){
        model.addAttribute("videoFormDto", new VideoFormDto());
        return "redirect:";
    }
}
