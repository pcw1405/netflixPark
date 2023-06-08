//package com.netf.netflix.Controller;
//
//import com.netf.netflix.Dto.ProfileImgDto;
////import com.netf.netflix.Entity.ProfileImg;
////import com.netf.netflix.Repository.ProfileImgRepository;
//import com.netf.netflix.Service.ProfileImgService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class ImageController {
//
//    private final ProfileImgService profileImgService;
////    private final ProfileImgRepository profileImgRepository;
//
//    @GetMapping("/profile-img/show")
//    public String showProfileImage(Model model) {
//        List<ProfileImg> profileImages = profileImgRepository.findAll();
//        model.addAttribute("profileImages", profileImages);
//        return "profile-img";
//    }
//
//    @PostMapping("/upload-profile-img")
//    public String uploadImage(@RequestParam("file") MultipartFile file) {
//        try {
//            ProfileImg profileImg = profileImgService.saveProfileImg(file);
//            profileImgRepository.save(profileImg);
//            return "redirect:/profile-img";
//        } catch (Exception e) {
//            return "error";
//        }
//    }
//
//    @GetMapping("/profile-img")
//    public String imgList(Model model) {
//        List<ProfileImg> imgList = profileImgService.profileImgSearch();
//        model.addAttribute("imgList", imgList);
//        return "profile-img";
//    }
//}
