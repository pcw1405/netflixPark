package com.netf.netflix.Video;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
}