package com.netf.netflix.Entity;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Dto.MemberFormDto;
import com.netf.netflix.Dto.VideoFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="video")
@Getter
@Setter
@ToString
public class Video{

    @Id
    @Column(name="video_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String videoNm; //영상이름

    @Column(nullable = false)
    private String cast; //감독이름

    @Column(nullable = false)
    private String actors; //배우이름

    @Column(nullable = false)
    private String description; //줄거리

    @Enumerated(EnumType.STRING)
    private VideoRole videoRole; //영화인지 드라마인지 ROLE

    @Column(nullable = false)
    private String genres;

    @Enumerated(EnumType.STRING)
    private VideoMaturityLevel videoMaturityLevel;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
    private VideoImg videoImg;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
    private VideoFile videoFile;

    private int viewCount = 0;

    public void updateVideo(VideoFormDto videoFormDto){
        this.videoNm = videoFormDto.getVideoNm();
        this.cast = videoFormDto.getCast();
        this.actors = videoFormDto.getActors();
        this.description = videoFormDto.getDescription();
        this.videoRole = videoFormDto.getVideoRole();
        this.videoMaturityLevel = videoFormDto.getVideoMaturityLevel();
        this.genres = videoFormDto.getGenres();
    }

//    public void addStock(int stockNumber){
//        this.stockNumber += stockNumber;
//    }

}