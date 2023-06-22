package com.netf.netflix.Service;

import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Dto.MemberFormDto;
import com.netf.netflix.Dto.VideoFormDto;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoFile;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.*;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;

    private final VideoImgRepository videoImgRepository;

    private final VideoFileRepository videoFileRepository;

    private final VideoImgService videoImgService;

    private final VideoFileService videoFileService;

    private final ProfileRepository profileRepository;

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

        //위에서 정보 등록을 한 이후에 각각의 MultipartFile들을 Service에서 처리
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

//        return videoRepository.findByVideoNmContainingOrCastContainingOrActorsContainingOrDescriptionContainingOrGenresContaining(searchKeyword, searchKeyword, searchKeyword, searchKeyword, searchKeyword);
        return videoRepository. findByVideoNmContainingOrActorsContainingOrGenresContaining(searchKeyword, searchKeyword, searchKeyword);

//        findByVideoNmContainingOrCastContainingOrActorsContaining
    }

    public void addViewCount(long videoId, MembershipRole membershipRole) {

        // 비디오 아이디를 이용하여 필요한 로직을 수행한 후에 데이터베이스에 저장하는 예시입니다.
        // 여기서는 간단히 Video 객체를 가져와서 출력하는 예시를 보여줍니다.
        Video targetVideo = videoRepository.findById(videoId).orElse(null);

        if (targetVideo != null) {
            System.out.println("Liked Video: " + targetVideo.getVideoNm());
            // 데이터베이스에 저장하는 로직을 추가하세요.

            // 비디오 조회수 증가
            if(membershipRole == MembershipRole.BASIC){
                int currentViewCount = targetVideo.getViewCount();
                if(targetVideo.getVideoMaturityLevel() == VideoMaturityLevel.KID){
                targetVideo.setViewCount(currentViewCount + 1);
                }
            }
            if(membershipRole == MembershipRole.STANDARD){
                int currentViewCount = targetVideo.getViewCount();
                if(targetVideo.getVideoRole() != VideoRole.MOVIE){
                targetVideo.setViewCount(currentViewCount + 1);
                }
            }
            if(membershipRole == MembershipRole.PREMIUM){
                int currentViewCount = targetVideo.getViewCount();
                targetVideo.setViewCount(currentViewCount + 1);
            }




            // 레포지토리에 저장
            videoRepository.save(targetVideo);

        }


    }
    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
    }


    public Long updateVideo(VideoFormDto videoFormDto, MultipartFile videoImgFile, MultipartFile videoFile) throws Exception{
        //수정할 videoId넘겨받아서 찾기
        Video video = videoRepository.findById(videoFormDto.getId()).orElse(null);
        //받은 정보로 update
        video.updateVideo(videoFormDto);

        //각각 img와 file찾아서 저장해주고
        VideoImg videoImg = videoImgRepository.findByVideo(video);
        videoImg.setVideo(video);
        VideoFile updatedVideoFile = videoFileRepository.findByVideo(video);
        updatedVideoFile.setVideo(video);
        //service를 통해서 update
        videoImgService.updateVideoImg(videoImg, videoImgFile);
        videoFileService.updateVideoFile(updatedVideoFile, videoFile);

        return video.getId();
    }

}