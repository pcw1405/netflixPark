package com.netf.netflix.Service;

import com.netf.netflix.Dto.MemberFormDto;
import com.netf.netflix.Dto.VideoFormDto;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoFile;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    private final VideoImgRepository videoImgRepository;

    private final VideoImgService videoImgService;

    private final VideoFileService videoFileService;

    public Long saveVideo(VideoFormDto videoFormDto, MultipartFile videoImgFile, MultipartFile videoFile) throws Exception{

        //영상정보 등록
        Video video = videoFormDto.createVideo();
        videoRepository.save(video);
        //영상이미지 등록
        VideoImg videoImg = new VideoImg();
        videoImg.setVideo(video);
        //영상 등록
        VideoFile newVideoFile = new VideoFile();
        newVideoFile.setVideo(video);

        videoImgService.saveVideoImg(videoImg, videoImgFile);
        videoFileService.saveVideoFile(newVideoFile, videoFile);
        return video.getId();
    }

    public List<Video> videoList() {
        return videoRepository.findAll();
    }

    public List<Video> videoSearchList(String searchKeyword) {
//        if (searchOption.equals("videoNm")) {
//            return videoRepository.findByVideoNmContaining(searchKeyword, pageable);
//        }
        // 기본적으로 videoNm을 검색 조건으로 사용합니다.
        return videoRepository.findByVideoNmContainingOrCastContainingOrActorsContainingOrDescriptionContainingOrGenresContaining(searchKeyword, searchKeyword, searchKeyword, searchKeyword, searchKeyword);

    }
}