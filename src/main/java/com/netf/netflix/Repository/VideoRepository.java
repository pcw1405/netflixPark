package com.netf.netflix.Repository;

import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findById(Long videoId);
    List<Video> findByVideoNmContaining(String searchKeyword);
    List<Video> findByGenresContaining(String searchKeyword);
    List<Video> findByVideoNmContainingOrCastContainingOrActorsContainingOrDescriptionContainingOrGenresContaining(String videoNm, String cast, String actors, String description, String genres);

//    List<Video> findByGenresInAndVideoRole(String genres, VideoRole videoRole);

    @Query("SELECT DISTINCT v.genres FROM Video v")
    List<String> findAllGenres();

    List<Video> findByVideoRole(VideoRole videoRole);

    @Query("SELECT DISTINCT v FROM Video v JOIN FETCH v.videoImg vi ORDER BY vi.uploadDate DESC")
    List<Video> findDistinctByOrderByVideoImgUploadDateDesc();

    List<Video> findTop10ByOrderByViewCountDesc();

    @Query("SELECT v FROM Video v")
    Page<Video> findPaginated(Pageable pageable);

}