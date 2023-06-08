package com.netf.netflix.Dto;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Constant.VideoRole;

import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoFile;
import com.netf.netflix.Entity.VideoImg;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;



@Getter
@Setter
public class MemberFormDto {


    private long id;


    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "이메일은 필수입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16,message = "비밀번호는 8자이상 ,16자이하로 입력해주세요")
    private String password;


    @Getter @Setter
    public static class VideoFormDto {

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

    @Getter
    @Setter
    public static class VideoFileDto {

        private Long id;

        private String fileName;

        private String oriFileName;

        private String fileUrl;

        private static ModelMapper modelMapper = new ModelMapper();

        public static VideoFileDto of(VideoFile videoFile){
            return modelMapper.map(videoFile, VideoFileDto.class);
        }
    }

    @Getter @Setter
    public static class VideoImgDto {
        private Long id;

        private String oriImgName;

        private String imgName;

        private String imgUrl;

        private static ModelMapper modelMapper = new ModelMapper();

        public static ProfileImgDto of(VideoImg videoImg){
            return modelMapper.map(videoImg, ProfileImgDto.class);
        }
    }
}
