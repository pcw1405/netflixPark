package com.netf.netflix.Video;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class VideoFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String videoNm;

    @NotNull(message = "감독명은 필수 입력 값입니다.")
    private String cast;

    @NotNull(message = "배우는 필수 입력 값입니다.")
    private String actors;

    @NotBlank(message = "줄거리는 필수 입력 값입니다.")
    private String description;

    private VideoRole videoRole;

    private List<VideoImgDto> videoImgDtoList = new ArrayList<>();

    private List<Long> videoImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Video createVideo(){
        return modelMapper.map(this, Video.class);
    }

    public static VideoFormDto of(Video video){
        return modelMapper.map(video, VideoFormDto.class);
    }

}
