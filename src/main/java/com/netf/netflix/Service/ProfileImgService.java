package com.netf.netflix.Service;

import com.netf.netflix.Dto.ProfileImgDto;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.ProfileImg;
import com.netf.netflix.Repository.ProfileImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ProfileImgService {

    @Value("${profileImgLocation}")
    private String profileImgLocation;

    private final ProfileImgRepository profileImgRepository;
    private final FileService fileService;

    public List<ProfileImg> getProfileImages() {
        return profileImgRepository.findAll();
    }

    public void uploadProfileImg(ProfileImg profileImg, MultipartFile file) throws Exception {
        String profileImgName = "";
        String profileOriImgName = file.getOriginalFilename();
        String profileImgUrl = "";

        profileImgName = fileService.uploadFile(profileImgLocation, profileOriImgName, file.getBytes());
        profileImgUrl = "/upload/profile_img/" + profileImgName;

        profileImg.setProfileImgName(profileImgName);
        profileImg.setProfileOriImgName(profileOriImgName);
        profileImg.setProfileImgUrl(profileImgUrl);

        profileImgRepository.save(profileImg);
    }

    public void saveProfileImage(Profile profile, ProfileImgDto profileImgDto) {
        ProfileImg profileImg = new ProfileImg();
        profileImg.setProfile(profile);
        profileImg.setProfileImgUrl(profileImgDto.getImageUrl());
        profileImgRepository.save(profileImg);
    }


    public ProfileImg getProfileImageById(Long imageId) {
        return profileImgRepository.findById(imageId).orElse(null);
    }

}
