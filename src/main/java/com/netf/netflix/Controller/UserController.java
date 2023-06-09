package com.netf.netflix.Controller;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    @GetMapping(value = "/user")
    public String userInfoLoad(HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        List<Profile> profiles = profileRepository.findByMember(member);

        model.addAttribute("member", member);
        model.addAttribute("profiles", profiles);

        return "user-profile/user";
    }
//
//    @PostMapping(value = "/user")
//    public String userUpdate(HttpSession session, Model model){
//        String loggedInUser = (String) session.getAttribute("loggedInUser");
//        Member member = memberRepository.findByEmail(loggedInUser);
//
//
//        return "";
//    }


}
