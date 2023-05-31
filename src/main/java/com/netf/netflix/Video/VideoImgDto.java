package com.netf.netflix.Video;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class VideoImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static VideoImgDto of(VideoImg videoImg){
        return modelMapper.map(videoImg, VideoImgDto.class);
    }
}
