package com.netf.netflix.Dto;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.ProfileImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;


@Getter @Setter @Component
public class ProfileImgDto {


    private Long id;
    private String profileImgName;
    private String profileOriImgName;
    private String imageUrl;
    private Profile profile;

    private static ModelMapper modelMapper = new ModelMapper();

    public void updateProfileFields() {
        ProfileImg profileImg = new ProfileImg();
        profileImg.setProfileImgUrl(this.imageUrl);
        this.profile.setProfileImg(profileImg);
    }

    public static ProfileImgDto or(ProfileImg profileImg) {
        ProfileImgDto profileImgDto = modelMapper.map(profileImg, ProfileImgDto.class);
        return profileImgDto;
    }
    }

