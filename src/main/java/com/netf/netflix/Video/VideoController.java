package com.netf.netflix.Video;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping(value = "/video/new")
    public String videoForm(Model model){
        model.addAttribute("videoFormDto", new VideoFormDto());
        return "videos/videoForm";
    }

    @PostMapping(value = "/video/upload")
    public String videoCreateFrom(@Valid VideoFormDto videoFormDto, BindingResult bindingResult, Model model,
                                   @RequestParam("videoImgFile") MultipartFile videoImgFile){
        if(bindingResult.hasErrors()){
            return "videos/videoForm";
        }
        try {
            videoService.saveVideo(videoFormDto, videoImgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품등록중 에러가 발생하였습니다.");
            return "videos/videoForm";
        }
        return "videos/videoForm";
    }

}
