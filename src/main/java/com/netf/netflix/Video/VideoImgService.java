package com.netf.netflix.Video;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class VideoImgService {

    @Value("${videoImgLocation}")
    private String videoImgLocation;

    private VideoImgRepository videoImgRepository;

    private final FileService fileService;

    public void saveVideoImg(VideoImg videoImg, MultipartFile videoImgFile)throws Exception{
        String oriImgName = videoImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(videoImgLocation, oriImgName,
                    videoImgFile.getBytes());
            imgUrl = "/upload/video_img/" + imgName;
        }
        videoImg.updatevideoImg(oriImgName, imgName, imgUrl);
        videoImgRepository.save(videoImg);
    }

}
