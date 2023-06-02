package com.netf.netflix.Video;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class VideoFormDto {

    private Long id;

    private String videoNm;

    private String cast;

    private String actors;

    private String description;

    private VideoRole videoRole;

    private VideoImgDto videoImgDto;

    private VideoFileDto videoFileDto;

    private String genres;

    private VideoMaturityLevel videoMaturityLevel;

    private static ModelMapper modelMapper = new ModelMapper();

    public Video createVideo(){
        return modelMapper.map(this, Video.class);
    }

    public static VideoFormDto of(Video video){
        return modelMapper.map(video, VideoFormDto.class);
    }


}
