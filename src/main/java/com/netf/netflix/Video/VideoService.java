package com.netf.netflix.Video;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    public Page<Video> videoList( Pageable pageable) {

        return videoRepository.findAll(pageable);
    }

    public Page<Video> videoSearchList(String searchKeyword, String searchOption, String videoRole, Pageable pageable) {
        String searchKeyword1 = searchOption.equals("videoNm") ? searchKeyword : "";
        String searchKeyword2 = searchOption.equals("genres") ? searchKeyword : "";
        String fixedVideoRole = "";  // 기본값은 빈 문자열로 설정

        if (videoRole.equals("MOVIE")) {
            fixedVideoRole = "MOVIE";
        } else if (videoRole.equals("DRAMA")) {
            fixedVideoRole = "DRAMA";
        }

        VideoRole role = VideoRole.valueOf(videoRole);

        return videoRepository.findByVideoNmContainingOrGenresContainingAndVideoRole(searchKeyword1, searchKeyword2, role, pageable);
    }
}