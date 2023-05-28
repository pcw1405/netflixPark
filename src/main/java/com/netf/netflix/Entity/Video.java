package com.netf.netflix.Entity;

import com.netf.netflix.constant.VideoRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Video")
public class Video {

    @Id
    @Column(name = "Video_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String VideoNm;

    private String director;

    private List<String> mainActors;

    private List<String> genres;

    private int views;

    @Enumerated(EnumType.STRING)
    private VideoRole videoRole;

}
