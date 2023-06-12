package com.netf.netflix.Controller;

import com.netf.netflix.Dto.ProfileDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final VideoImgRepository videoImgRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/home/{profileId}")
    public String showProfileHome(@PathVariable("profileId") Long profileId, Model model,HttpSession session) {
        session.setAttribute("profileNm",profileId);
        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }

        // 모델에 선택된 프로필 정보를 추가
        model.addAttribute("selectedProfile", selectedProfile);

        // 나머지 프로필 정보를 가져와서 모델에 추가
        List<Profile> otherProfiles = profileRepository.findByMember(selectedProfile.getMember())
                .stream()
                .filter(profile -> !profile.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);
//        (하단내용 movie drama mylist 추가해야함)
        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);
        return "home"; // 헤더 템플릿을 리턴하도록 수정해주세요.
    }
//    @GetMapping("/profile/{id}")

    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
        List<Profile> profiles = profileRepository.findByMember(member);
//        System.out.println("profiles : " + profiles);
        model.addAttribute("profiles", profiles);
        return "profile";
    }

    @GetMapping("/profile-new")
    public String profileForm(Model model) {
        model.addAttribute("profileDto", new ProfileDto());
        return "profile-new";
    }

    @PostMapping("/saveProfile")
    public String saveProfile(@Valid @ModelAttribute("profileDto") ProfileDto profileDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors occurred: {}", bindingResult.getAllErrors());
            return "profile-new";
        }

        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }

        profileDto.updateProfileFields(); // 프로필 필드 값을 업데이트

        Profile profile = profileDto.getProfile();
        profile.setMember(member);
        profileService.saveProfile(profile);
        return "redirect:/profile/profile";
    }
    @GetMapping("/profile-update")
    public String updateProfile(Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 수 없습니다.");
        }
        List<Profile> profiles = profileRepository.findByMember(member);
        model.addAttribute("profiles", profiles);
        return "profile-update";
    }

    @GetMapping("/profile-updateform/{profileId}")
    public String updateProfileForm(@PathVariable("profileId") Long profileId, Model model, HttpSession session) {
        session.setAttribute("profileId", profileId);
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isEmpty()) {
            throw new RuntimeException("프로필 정보를 찾을 수 없습니다.");
        }
        Profile profile = optionalProfile.get();
        model.addAttribute("profile", profile);
        return "profile-updateform";
    }

    @PostMapping("/updateProfile")
    public String updateProfileDto(@ModelAttribute("profiles") @Valid ProfileDto profileDto, BindingResult bindingResult, HttpSession session) {
        Long profileId = (Long) session.getAttribute("profileId");
        if (profileId == null) {
            throw new RuntimeException("프로필 ID를 찾을 수 없습니다.");
        }

        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }

        Profile profile = profileService.getProfileById(profileId);
        if (profile == null) {
            throw new RuntimeException("프로필 정보를 찾을 수 없습니다.");
        }

        if (bindingResult.hasErrors()) {
            // 유효성 검사 에러 처리
            return "profile-form";
        }

        // 프로필 필드 값을 업데이트
        profileService.updateProfile(profile, profileDto.getName(), profileDto.getLanguage(), profileDto.getNickname(), profileDto.getMaturityLevel());
//        session.setAttribute("loggedInUser", profile);

        return "redirect:/profile/profile";
    }

}

