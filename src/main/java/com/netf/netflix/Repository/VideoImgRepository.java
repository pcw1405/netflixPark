package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoImgRepository extends JpaRepository<VideoImg, Long> {
    VideoImg findByVideo(Video video);
}
