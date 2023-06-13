package com.netf.netflix.Controller;

import com.netf.netflix.Config.CustomUserDetails;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MemberService memberService;

    @GetMapping(value = "/user")
    public String userInfoLoad(HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        List<Profile> profiles = profileRepository.findByMember(member);

        model.addAttribute("member", member);
        model.addAttribute("profiles", profiles);

        return "user-profile/user";
    }

    @PostMapping(value = "/user-changePw")
    public String updatePassword(@RequestParam("password") String password,
                                 HttpSession session)throws Exception{
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        String encodedPassword = passwordEncoder.encode(password);
        member.setPassword(encodedPassword);

        memberService.changePassword(member, encodedPassword);

        session.setAttribute("loggedInUser", member.getEmail());
        log.info("정상적으로 비밀번호가 바꼈음");
        return "redirect:/user";
    }

    @PostMapping(value = "/user-changePhoneNum")
    public String updatePhoneNum(@RequestParam("phoneNum") String phoneNum,
                                 HttpSession session)throws Exception{
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);

        memberService.changePhoneNumber(member, phoneNum);

        session.setAttribute("loggedInUser", member.getEmail());
        return "redirect:/user";
    }

    @PostMapping(value = "/member-secession")
    public String memberSecession(@RequestParam("membershipDrop") String membershipDrop,
                                  HttpSession session){
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if(membershipDrop.equals("NONE")){
            memberService.membershipDrop(member);
        }
        session.setAttribute("loggedInUser", member.getEmail());
        return "redirect:/user";
    }

    @PostMapping("/profile-delete")
    public String userProfileDelete(@RequestParam("profileId") Long profileId){

        profileRepository.deleteById(profileId);

        return "redirect:/user";
    }


}
