package com.netf.netflix.Video;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class VideoImgService {

    @Value("${videoImgLocation}")
    private String videoImgLocation;

    private final VideoImgRepository videoImgRepository;

    private final FileService fileService;

    public void saveVideoImg(VideoImg videoImg, MultipartFile videoImgFile)throws Exception{
        String oriImgName = videoImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        imgName = fileService.uploadFile(videoImgLocation, oriImgName,
                videoImgFile.getBytes());
        imgUrl = "/upload/video_img/" + imgName;

        videoImg.createdVideoImg(oriImgName, imgName, imgUrl);

        videoImgRepository.save(videoImg);

    }

}
