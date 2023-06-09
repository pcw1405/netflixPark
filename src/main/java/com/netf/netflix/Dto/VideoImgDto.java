package com.netf.netflix.Dto;

import com.netf.netflix.Entity.VideoImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter
@Setter
public class VideoImgDto {
    private Long id;

    private String oriImgName;

    private String imgName;

    private String imgUrl;

    private Date uploadDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public static VideoImgDto of(VideoImg videoImg){
        VideoImgDto videoImgDto = modelMapper.map(videoImg, VideoImgDto.class);
        videoImgDto.setUploadDate(videoImg.getUploadDate());
        return videoImgDto;
    }
}