package com.netf.netflix.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findByVideoNmContaining(String searchKeyword,Pageable pageable);
    Page<Video> findByGenresContaining(String searchKeyword,Pageable pageable);

    Page<Video> findByVideoNmContainingOrGenresContainingAndVideoRole(String searchKeyword1, String searchKeyword2, VideoRole videoRole, Pageable pageable);

}