package com.netf.netflix.Video;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class VideoFormDto {

    private Long id;

    @NotEmpty(message = "영화명은 필수 입력 값입니다.")
    private String videoNm;

    @NotEmpty(message = "감독명은 필수 입력 값입니다.")
    private String cast;

    @NotEmpty(message = "배우는 필수 입력 값입니다.")
    private String actors;

    @NotEmpty(message = "줄거리는 필수 입력 값입니다.")
    private String description;

    @NotEmpty(message = "영화/드라마 구분을 해주세요")
    private VideoRole videoRole;

    @NotEmpty(message = "메인이미지를 넣어주세요")
    private VideoImgDto videoImgDto;

    @NotEmpty(message = "메인 영상을 넣어주세요")
    private VideoFileDto videoFileDto;

    private static ModelMapper modelMapper = new ModelMapper();

    public Video createVideo(){
        return modelMapper.map(this, Video.class);
    }

    public static VideoFormDto of(Video video){
        return modelMapper.map(video, VideoFormDto.class);
    }


}
