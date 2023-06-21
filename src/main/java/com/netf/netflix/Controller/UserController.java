package com.netf.netflix.Controller;

import com.netf.netflix.Config.CustomUserDetails;
import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final MemberService memberService;
    private final VideoImgRepository videoImgRepository;

    @GetMapping(value = "/user")
    public String userInfoLoad(HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);

        List<Profile> profiles = profileRepository.findByMember(member);
        List<String> profileNames = profiles.stream()
                .map(Profile::getName)
                .collect(Collectors.toList());
        Profile selectedProfile = profiles.get(0);
        model.addAttribute("selectedProfile", selectedProfile);

        // 나머지 프로필 정보를 가져와서 모델에 추가
        List<Profile> otherProfiles = profiles.stream()
                .filter(profile -> !profile.getId().equals(selectedProfile.getId()))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);



        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);
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

    @PostMapping(value = "/profile-delete")
    public String userProfileDelete(@RequestParam("profileId") Long profileId){

        profileRepository.deleteById(profileId);

        return "redirect:/user";
    }

    @GetMapping("/user/membershipCheck")
    public ResponseEntity<?> userMembershipCheck(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        MembershipRole membership = member.getMembershipRole();
        LocalDate membershipDate = member.getMembershipExpirationDate();

        if(membership == MembershipRole.NONE){
            response.put("membershipdate", "");
        }else {
            response.put("membershipdate", membershipDate);
        }

        return ResponseEntity.ok(response);
    }
}
