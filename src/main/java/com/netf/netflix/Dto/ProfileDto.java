package com.netf.netflix.Dto;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.ProfileImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

    private ProfileImg profileImg;

    private Long id;
    private String name;
    private String language;
    private String nickname;
    private Profile.MaturityLevel maturityLevel;
    private String imageUrl;
    private Profile profile;
    public ProfileDto() {
        this.profile= new Profile();
    }
    public void updateProfileFields() {
        profile.setImageUrl(imageUrl);
        profile.setName(name);
        profile.setLanguage(language);
        profile.setNickname(nickname);
        profile.setMaturityLevel(maturityLevel);
        // 필요한 경우 다른 필드들도 설정해주세요
    }

    public void setProfileImgDto(ProfileImgDto profileImgDto) {
        this.profileImg = new ProfileImg();
        this.profileImg.setProfileImgName(profileImgDto.getProfileImgName());
        this.profileImg.setProfileOriImgName(profileImgDto.getProfileOriImgName());
        this.profileImg.setImageUrl(profileImgDto.getImageUrl());
    }
}
