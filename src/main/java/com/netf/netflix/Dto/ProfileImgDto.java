package com.netf.netflix.Dto;

//import com.netf.netflix.Entity.ProfileImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ProfileImgDto {
    private Long id;

    private String oriImgName;

    private String imgName;

    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();}

//    public static ProfileImgDto of(ProfileImg profileImg) {
//        return modelMapper.map(profileImg, ProfileImgDto.class);
//    }
//
//    public ProfileImg toEntity() {
//        return modelMapper.map(this, ProfileImg.class);
//    }
//    public void setProfileImgUrl(String profileImgUrl) {
//        this.imgUrl = profileImgUrl;
//    }
//    public String getProfileImgUrl() {
//        return imgUrl;
//    }
//}
