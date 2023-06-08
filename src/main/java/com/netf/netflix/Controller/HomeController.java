package com.netf.netflix.Controller;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/")
    public String home(Model model, Principal principal) {
        // 로그인된 멤버의 정보 가져오기
        String email = principal.getName();
        Member member = memberService.findMemberByEmail(email);

        // 멤버의 프로필 리스트 가져오기
        List<Profile> profiles = member.getProfiles();

        // 프로필 정보를 모델에 추가
        List<String> profileNames = new ArrayList<>();
//        List<String> profileImages = new ArrayList<>();
        for (Profile profile : profiles) {
            profileNames.add(profile.getName());
//            profileImages.add(profile.getImage());
        }

//        model.addAttribute("Profiles",profiles);
        model.addAttribute("profileNames", profileNames);
//        model.addAttribute("profileImages", profileImages);
        return "home";
    }
    @GetMapping(value = "/user")
    public String drama(){
        return "user-profile/user";
    }
}
