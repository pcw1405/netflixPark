package com.netf.netflix.Entity;

import com.netf.netflix.Entity.Video;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "video_file")
@Getter
@Setter
@ToString
public class VideoFile {
    @Id
    @Column(name="video_file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String oriFileName;

    @Column
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    private Video video;

    public void createdVideoFile(String oriFileName, String fileName, String fileUrl){
        this.fileName = fileName;
        this.oriFileName = oriFileName;
        this.fileUrl = fileUrl;
    }

}
