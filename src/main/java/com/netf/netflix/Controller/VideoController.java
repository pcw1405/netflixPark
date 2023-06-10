package com.netf.netflix.Controller;

import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Dto.MemberFormDto;
import com.netf.netflix.Dto.VideoFormDto;
import com.netf.netflix.Dto.VideoImgDto;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import com.netf.netflix.Service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@Log
public class VideoController {

    private final VideoService videoService;
    private final VideoImgRepository videoImgRepository;
    private final VideoRepository videoRepository;

//    private static final List<VideoImgDto> uploadedVideoList = new ArrayList<>();


    @GetMapping(value = "/video/new")
    public String videoForm(Model model) {
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
    public String videoList(Model model, String searchKeyword) {
        List<Video> list = null;

        if (searchKeyword == null) {
            list = null;
        } else {
            list = videoService.videoSearchList(searchKeyword);
        }

        model.addAttribute("list", list);
        model.addAttribute("uploadedVideoList", videoImgRepository.findAll()); // 이미지 정보 조회

        return "/rightmain/search";
    }
    @GetMapping("/drama")
    public String dramaList( Model model){

          List<String> subjects = videoRepository.findAllGenres();
        List<Video> videos = videoRepository.findByVideoRole(VideoRole.DRAMA);

        for (Video video : videos) {
            System.out.println(video.getId()+video.getVideoNm()+video.getGenres()+video.getDescription());
        }

        if (!videos.isEmpty()) {
            // 랜덤한 인덱스를 생성
            int randomIndex = new Random().nextInt(videos.size());
            // 랜덤한 비디오 선택
            Video randomVideo = videos.get(randomIndex);
            // 선택한 비디오를 모델에 추가
            model.addAttribute("randomVideo", randomVideo);
        }


        model.addAttribute("subjects",subjects);
        model.addAttribute("videos",videos);


        return "/leftmain/drama";
    }

    @GetMapping("/movie")
    public String movieList( Model model){

        List<String> subjects = videoRepository.findAllGenres();
        List<Video> videos = videoRepository.findByVideoRole(VideoRole.MOVIE);

        for (Video video : videos) {
            System.out.println(video.getId()+video.getVideoNm()+video.getGenres()+video.getDescription());
        }

        if (!videos.isEmpty()) {
            // 랜덤한 인덱스를 생성
            int randomIndex = new Random().nextInt(videos.size());
            // 랜덤한 비디오 선택
            Video randomVideo = videos.get(randomIndex);
            // 선택한 비디오를 모델에 추가
            model.addAttribute("randomVideo", randomVideo);
        }


        model.addAttribute("subjects",subjects);
        model.addAttribute("videos",videos);


        return "/leftmain/movies";
    }

//    @PostMapping("/save-like")
//    public ResponseEntity<String> saveLike(@RequestParam("videoId") Long videoId, HttpSession session) {
//        String loggedInUser = (String) session.getAttribute("loggedInUser");
//        videoService.saveLike(videoId);
//
//
//        return ResponseEntity.ok("Like saved successfully");
//    }

}
