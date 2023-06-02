package com.netf.netflix.Controller;

import com.netf.netflix.Dto.ProfileDto;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileController(ProfileRepository profileRepository){
        this.profileRepository= profileRepository;
    }

    @PostMapping("/saveProfile")
    @ResponseBody
    public String saveProfile(@RequestBody ProfileDto profileDto) {
        Profile profile = new Profile();
        profile.setName(profileDto.getName());
        profile.setLanguage(profileDto.getLanguage());
        profile.setNickname(profileDto.getNickname());
        profile.setMaturityLevel(profileDto.getMaturityLevel());
        profile.setProfileImageUrl(profileDto.getProfileImageUrl());

        profileRepository.save(profile);

        return "프로필이 저장되었습니다.";
    }
}
