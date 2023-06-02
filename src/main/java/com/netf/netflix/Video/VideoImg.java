package com.netf.netflix.Video;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "video_img")
@Getter
@Setter
@ToString
public class VideoImg {
    @Id
    @Column(name="video_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String imgName;

    @Column
    private String oriImgName;

    @Column
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private Video video;

    public void createdVideoImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}
