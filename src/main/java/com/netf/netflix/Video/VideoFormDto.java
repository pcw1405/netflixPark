package com.netf.netflix.Video;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class VideoFormDto {

    private Long id;

    @NotNull(message = "영화명은 필수 입력 값입니다.")
    private String videoNm;

    @NotNull(message = "감독명은 필수 입력 값입니다.")
    private String cast;

    @NotNull(message = "배우는 필수 입력 값입니다.")
    private String actors;

    @NotNull(message = "줄거리는 필수 입력 값입니다.")
    private String description;

    private VideoRole videoRole;

    private VideoImgDto videoImgDto;

    private VideoFileDto videoFileDto;

    private static ModelMapper modelMapper = new ModelMapper();

    public Video createVideo(){
        return modelMapper.map(this, Video.class);
    }

    public static VideoFormDto of(Video video){
        return modelMapper.map(video, VideoFormDto.class);
    }


}
