package com.netf.netflix.Dto;

import com.netf.netflix.Entity.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private String name;
    private String language;
    private String nickname;
    private String maturityLevel;
//    private String profileImageUrl;
    private String imagePath;
    private Profile profile;
    public ProfileDto() {
        this.profile= new Profile();
    }
    public void updateProfileFields() {
        profile.setName(name);
        profile.setLanguage(language);
        profile.setNickname(nickname);
        profile.setMaturityLevel(maturityLevel);
        // 필요한 경우 다른 필드들도 설정해주세요
    }
//    public String getProfileImageUrl() {
//        return profileImageUrl;
//    }
//
//    // 프로필 이미지 URL 설정
//    public void setProfileImageUrl(String profileImageUrl) {
//        this.profileImageUrl = profileImageUrl;
//    }

}
