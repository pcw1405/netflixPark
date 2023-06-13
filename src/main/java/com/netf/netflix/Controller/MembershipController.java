package com.netf.netflix.Controller;


import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Service.MembershipService;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final VideoImgRepository videoImgRepository;



    @GetMapping(value = "/membership")
    public String membershipMain(Model model, Principal principal){
        String email = principal.getName();
        Member member=memberService.findMemberByEmail(email);
        List<Profile>profiles=profileRepository.findByMember(member);
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

        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);


        return "/membership/membershipSelect";
    }

    @PostMapping(value = "/membership/pay")
    public String membershipPay(@RequestParam("membershipRole")MembershipRole membershipRole,
                                Model model, HttpSession session){
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        LocalDate membershipExpirationDate = member.getMembershipExpirationDate();
        LocalDate currentDate = LocalDate.now();

        if(membershipExpirationDate != null) {
            LocalDate newExpirationDate = currentDate.plusMonths(1);
            membershipService.changeMembershipRole(member, membershipRole, newExpirationDate);
            return "redirect:/user";
        }else{
            LocalDate newExpirationDate = currentDate.plusMonths(1);
            membershipService.changeMembershipRole(member, membershipRole, newExpirationDate);
            return "redirect:/user";
        }
    }



}
