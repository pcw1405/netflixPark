package com.netf.netflix.Service;

import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Entity.VideoFile;
import com.netf.netflix.Repository.VideoFileRepository;
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

    public void updateVideoFile(VideoFile videoFile, MultipartFile videoClipFile) throws Exception {
        if(!videoClipFile.isEmpty()){
            String oriFileName = videoClipFile.getOriginalFilename();
            String fileName = "";
            String fileUrl = "";

            fileName = fileService.uploadFile(videoFileLocation, oriFileName, videoClipFile.getBytes());
            fileUrl = "/upload/video_file/" + fileName;

            if (videoFile.getFileName() != null) {
                String filePath = videoFileLocation + "/" + videoFile.getFileName();
                fileService.deleteFile(filePath);
            }

            videoFile.createdVideoFile(oriFileName, fileName, fileUrl);

            videoFileRepository.save(videoFile);
        }
    }



}
