package com.netf.netflix.Dto;


import com.netf.netflix.Entity.VideoFile;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class VideoFileDto {

    private Long id;

    private String fileName;

    private String oriFileName;

    private String fileUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static VideoFileDto of(VideoFile videoFile){
        return modelMapper.map(videoFile, VideoFileDto.class);
    }
}