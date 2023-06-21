package com.netf.netflix.Service;

import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.VideoImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class VideoImgService {

    @Value("${videoImgLocation}")
    private String videoImgLocation;

    private final VideoImgRepository videoImgRepository;
    private final FileService fileService;

    public void saveVideoImg(VideoImg videoImg, MultipartFile videoImgFile) throws Exception {
        String oriImgName = videoImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        imgName = fileService.uploadFile(videoImgLocation, oriImgName, videoImgFile.getBytes());
        imgUrl = "/upload/video_img/" + imgName;

        videoImg.createdVideoImg(oriImgName, imgName, imgUrl);
        videoImgRepository.save(videoImg);
    }

    public void updateVideoImg(VideoImg videoImg, MultipartFile videoImgFile) throws Exception {
        if(!videoImgFile.isEmpty()){
            String oriImgName = videoImgFile.getOriginalFilename();
            String imgName = "";
            String imgUrl = "";

            imgName = fileService.uploadFile(videoImgLocation, oriImgName, videoImgFile.getBytes());
            imgUrl = "/upload/video_img/" + imgName;
            
            //기존에 저장되어 있는 파일과 다를경우에는 원래있던 파일 삭제
            if (videoImg.getImgName() != null) {
                String filePath = videoImgLocation + "/" + videoImg.getImgName();
                fileService.deleteFile(filePath);
            }

            videoImg.createdVideoImg(oriImgName, imgName, imgUrl);
            videoImgRepository.save(videoImg);
        }
    }
}
