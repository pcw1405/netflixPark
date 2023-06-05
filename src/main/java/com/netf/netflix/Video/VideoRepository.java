package com.netf.netflix.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByVideoNmContaining(String searchKeyword);
    List<Video> findByGenresContaining(String searchKeyword);

//    Page<Video> findByVideoNmContainingOrGenresContainingAndVideoRole(String searchKeyword1, String searchKeyword2, VideoRole videoRole, Pageable pageable);

}