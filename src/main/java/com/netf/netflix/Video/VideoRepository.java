package com.netf.netflix.Video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByVideoNmContaining(String searchKeyword);
    List<Video> findByGenresContaining(String searchKeyword);
    List<Video> findByVideoNmContainingOrCastContainingOrActorsContainingOrDescriptionContainingOrGenresContaining(String videoNm, String cast, String actors, String description, String genres);
}