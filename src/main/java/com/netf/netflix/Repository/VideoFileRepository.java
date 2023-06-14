package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoFileRepository extends JpaRepository<VideoFile, Long> {

    VideoFile findByVideo(Video video);
}
