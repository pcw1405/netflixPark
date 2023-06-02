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

    public Long saveVideo(VideoFormDto videoFormDto, MultipartFile videoImgFile) throws Exception{

        //상품 등록
        Video video = videoFormDto.createVideo();
        videoRepository.save(video);
        VideoImg videoImg = new VideoImg();
        videoImg.setVideo(video);
        videoImgService.saveVideoImg(videoImg, videoImgFile);

        return video.getId();
    }
}