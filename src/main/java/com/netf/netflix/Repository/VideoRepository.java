package com.netf.netflix.Repository;

import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByVideoNmContaining(String searchKeyword);
    List<Video> findByGenresContaining(String searchKeyword);
    List<Video> findByVideoNmContainingOrCastContainingOrActorsContainingOrDescriptionContainingOrGenresContaining(String videoNm, String cast, String actors, String description, String genres);

//    List<Video> findByGenresInAndVideoRole(String genres, VideoRole videoRole);

    @Query("SELECT DISTINCT v.genres FROM Video v")
    List<String> findAllGenres();

    List<Video> findByVideoRole(VideoRole videoRole);

}