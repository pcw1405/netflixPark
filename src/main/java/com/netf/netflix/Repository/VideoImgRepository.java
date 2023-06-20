package com.netf.netflix.Repository;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoImgRepository extends JpaRepository<VideoImg, Long> {
    VideoImg findByVideo(Video video);
    List<VideoImg> findByVideoVideoMaturityLevel(VideoMaturityLevel videoMaturityLevel);

}
