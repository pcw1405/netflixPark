//package com.netf.netflix.Service;
//
////import com.netf.netflix.Entity.ProfileImg;
////import com.netf.netflix.Repository.ProfileImgRepository;
//import com.netf.netflix.Repository.ProfileRepository;
//import com.netf.netflix.Service.FileService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class ProfileImgService {
//
//    @Value("${profileImgLocation}")
//    private String profileImgLocation;
//
//    private final ProfileImgRepository profileImgRepository;
//    private final FileService fileService;
//    private final ProfileRepository profileRepository;
//
//    public ProfileImg saveProfileImg(MultipartFile profileImgFile) throws Exception {
//        String oriImgName = profileImgFile.getOriginalFilename();
//        String imgName = fileService.uploadFile(profileImgLocation, profileImgFile.getOriginalFilename(), profileImgFile.getBytes());
//        String imgUrl = "/upload/profile_img/" + imgName;
//
//        ProfileImg profileImg = new ProfileImg();
//        profileImg.setProfileOriImgName(oriImgName);
//        profileImg.setProfileImgName(imgName);
//        profileImg.setProfileImgUrl(imgUrl);
//
//        return profileImgRepository.save(profileImg);
//    }
//
//    public List<ProfileImg> profileImgSearch() {
//        return profileImgRepository.findAll();
//    }
//}
