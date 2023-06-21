package com.netf.netflix.Controller;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.ProfileImg;
import com.netf.netflix.Repository.ProfileImgRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Service.ProfileImgService;
import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProfileImgController {

    private final ProfileImgService profileImgService;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    @GetMapping("/profile-img")
    public String showProfileImgForm(Model model) {
        List<ProfileImg> imgList = profileImgService.getProfileImages();
        model.addAttribute("imgList", imgList);
        model.addAttribute("profileImg", new ProfileImg());
        return "profile-img";
    }
    @GetMapping("/profile-update-img/{profileId}")
    public String showProfileImgUpdateForm(Model model) {
        List<ProfileImg> imgList = profileImgService.getProfileImages();
        model.addAttribute("imgList", imgList);
        model.addAttribute("profileImg", new ProfileImg());
        return "profile-update-img";
    }
    @PostMapping("/profile-update-img/{profileId}")
    public String updateProfileImg(Model model,HttpSession session,
                                   @RequestParam("kduimgurl") String imgUrl) {
        Long profileId = (Long) session.getAttribute("profileId");
        Profile profile = profileRepository.findById(profileId).orElse(null);

        profile.setImageUrl("http://localhost:8081" + imgUrl);
        profileRepository.save(profile);

        // 업데이트 후 리디렉션할 경로를 반환합니다.
        return "redirect:/profile/profile-updateform/" + profileId;
    }

    @PostMapping("/profile-img")
    public String handleProfileImgUpload(@Valid @ModelAttribute("profileImg") ProfileImg profileImg,
                                         BindingResult bindingResult,
                                         @RequestParam("profileImg") MultipartFile profileImgFile,
                                         Model model) {
        if (bindingResult.hasErrors()) {
            return "profile-img";
        }
        try {
            profileImgService.uploadProfileImg(profileImg, profileImgFile);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "이미지 업로드 중 문제가 발생했습니다.");
            return "profile-img";
        }
        return "redirect:/profile-img";
    }

}
