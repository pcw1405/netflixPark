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
public class VideoFileService {

    @Value("${videoFileLocation}")
    private String videoFileLocation;

    private final VideoFileRepository videoFileRepository;

    private final FileService fileService;

    public void saveVideoFile(VideoFile videoFile, MultipartFile videoImgFile)throws Exception{
        String oriImgName = videoImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        imgName = fileService.uploadFile(videoFileLocation, oriImgName,
                videoImgFile.getBytes());
        imgUrl = "/upload/video_file/" + imgName;

        videoFile.createdVideoFile(oriImgName, imgName, imgUrl);

        videoFileRepository.save(videoFile);
    }

}
