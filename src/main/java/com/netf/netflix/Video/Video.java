package com.netf.netflix.Video;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="video")
@Getter
@Setter
@ToString
public class Video{

    @Id
    @Column(name="video_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void updateItem(VideoFormDto videoFormDto){
        this.videoNm = videoFormDto.getVideoNm();
        this.cast = videoFormDto.getCast();
        this.actors = videoFormDto.getActors();
        this.description = videoFormDto.getDescription();
        this.videoRole = videoFormDto.getVideoRole();
    }

//    public void addStock(int stockNumber){
//        this.stockNumber += stockNumber;
//    }

}