package com.netf.netflix.Controller;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Service.MemberService;
import com.netf.netflix.Service.ProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final MemberService memberService;
    private final ProfileRepository profileRepository;


    public HomeController(MemberService memberService, ProfileRepository profileRepository) {
        this.memberService = memberService;
        this.profileRepository = profileRepository;

    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        // 로그인된 멤버의 정보 가져오기
        String email = principal.getName();
        Member member = memberService.findMemberByEmail(email);

        // 멤버의 프로필 리스트 가져오기
        List<Profile> profiles = profileRepository.findByMember(member);

        // 프로필 이름 리스트 가져오기
        List<String> profileNames = profiles.stream()
                .map(Profile::getName)
                .collect(Collectors.toList());

        // 첫 번째 프로필을 선택된 프로필로 설정
        Profile selectedProfile = profiles.get(0);
        model.addAttribute("selectedProfile", selectedProfile);

        // 나머지 프로필 정보를 가져와서 모델에 추가
        List<Profile> otherProfiles = profiles.stream()
                .filter(profile -> !profile.getId().equals(selectedProfile.getId()))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

        return "home";
    }

}
