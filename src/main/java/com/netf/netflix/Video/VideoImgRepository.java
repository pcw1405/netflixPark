package com.netf.netflix.Video;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoImgRepository extends JpaRepository<VideoImg, Long> {
    VideoImg findByVideo(Video video);
}
