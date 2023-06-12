package com.netf.netflix.Controller;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.ProfileImg;
import com.netf.netflix.Service.ProfileImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileImgController {

    private final ProfileImgService profileImgService;

    @GetMapping("/profile-img")
    public String showProfileImgForm(Model model) {
        List<ProfileImg> imgList = profileImgService.getProfileImages();
        model.addAttribute("imgList", imgList);
        model.addAttribute("profileImg", new ProfileImg());
        return "profile-img";
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
