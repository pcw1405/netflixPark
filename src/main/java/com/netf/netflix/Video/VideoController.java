package com.netf.netflix.Video;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log
public class VideoController {

    private final VideoService videoService;

    @GetMapping(value = "/video/new")
    public String videoForm(Model model){
        model.addAttribute("videoFormDto", new VideoFormDto());
        return "videos/videoForm";
    }

    @PostMapping(value = "/video/new")
    public String videoCreateFrom(@Valid VideoFormDto videoFormDto, BindingResult bindingResult, Model model,
                                   @RequestParam("videoImgFile") MultipartFile videoImgFile,
                                  @RequestParam("videoFile") MultipartFile videoFile){

        if(bindingResult.hasErrors()){
            return "videos/videoForm";
        }
        try {
            videoService.saveVideo(videoFormDto, videoImgFile, videoFile);
        } catch (Exception e) {
            return "videos/videoForm";
        }
        return "videos/videoForm";
    }
}
