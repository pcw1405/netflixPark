package com.netf.netflix.Video;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log
public class VideoController {

    private final VideoService videoService;
    private final VideoImgRepository videoImgRepository;



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
            model.addAttribute("errorMessage","영상업로드중 문제가 발생하였습니다.");
            return "videos/videoForm";
        }
        return "videos/videoForm";
    }

    @GetMapping("/search")
    public String videoList(Model model,

                            String searchKeyword){

        List<Video> list = null;

        if (searchKeyword ==null){
            list=null;
        }else{
            list=videoService.videoSearchList(searchKeyword);
        }


//        List<Video> videoList = new ArrayList<>();
//        for (Video video : list) {
//            VideoImg videoImg = video.getVideoImg();
//            if (videoImg != null) {
//                String imgUrl = videoImg.getImgUrl();
//                videoImg.setImgUrl(imgUrl);
//                videoList.add(video);
//                System.out.println(imgUrl);
//            }
//        }

//        int nowPage=list.getPageable().getPageNumber() +1;
//        int startPage=Math.max(nowPage-4,1);
//        int endPage=Math.min(nowPage+5,list.getTotalPages());
//        String imgLocation="/temp";
//        model.addAttribute("imgLocation", imgLocation);
        model.addAttribute("list",list);
//        model.addAttribute("nowPage",nowPage);
//        model.addAttribute("startPage",startPage);
//        model.addAttribute("endPage",endPage);

        return "/rightmain/search";
    }
}
