package com.netf.netflix.Video;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private String videoNm; //상품명

    @Column(nullable = false)
    private String cast;

    @Column(nullable = false)
    private String actors;

    @Column(nullable = false)
    private String description; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private VideoRole videoRole; //상품 판매 상태


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