package com.netf.netflix.Dto;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Entity.Video;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
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

    public Video updateVideo(){
        return modelMapper.map(this, Video.class);
    }

    public static VideoFormDto of(Video video){
        return modelMapper.map(video, VideoFormDto.class);
    }


}
