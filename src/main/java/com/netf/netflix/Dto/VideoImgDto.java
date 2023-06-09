package com.netf.netflix.Dto;

import com.netf.netflix.Dto.ProfileImgDto;
import com.netf.netflix.Entity.VideoImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class VideoImgDto {
    private Long id;

    private String oriImgName;

    private String imgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ProfileImgDto of(VideoImg videoImg){
        return modelMapper.map(videoImg, ProfileImgDto.class);
    }
}