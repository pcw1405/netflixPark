package com.netf.netflix.Dto;

import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Constant.Role;
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

    @NotEmpty(message = "전화번호는 필수 입력값입니다.")
    private String phoneNumber;

    private String role;

    private String membershipRole;
}
