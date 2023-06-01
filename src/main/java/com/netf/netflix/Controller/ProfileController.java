package com.netf.netflix.Controller;

import com.netf.netflix.Config.AuditorAwareImpl;
import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final AuditorAware<String> auditorAware;
    private final MemberRepository memberRepository;

//    public ProfileController( ProfileService profileService,ProfileRepository profileRepository,AuditorAware<String> auditorAware) {
//        this.profileService = profileService;
//        this.profileRepository=profileRepository;
//        this.auditorAware = auditorAware;
//    }

    @GetMapping("/profile")
    public String getProfiles( Model model) {
        AuditorAware<String> auditorAware = new AuditorAwareImpl();

        System.out.println("profile start");

        Optional<String> memberIdOptional = auditorAware.getCurrentAuditor();
        if (memberIdOptional.isPresent()) {
            String memberId = memberIdOptional.get();
            // memberId를 사용하여 필요한 정보를 가져온다

            // memberId를 이용하여 멤버를 조회
            Optional<Member> memberOptional = Optional.ofNullable(memberRepository.findByEmail(memberId));
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();

                // 멤버의 프로파일들의 정보를 가져온다
                List<Profile> profiles = member.getProfiles();

                Profile newProfile = new Profile();
                newProfile.setName("abcd");
                newProfile.setMember(member);
                // 나머지 필드 값 설정
                newProfile.setImg_url("/images/icons/user1.png");

                // profiles에 새로운 프로필 추가
                profiles.add(newProfile);

                Profile newProfile1 = new Profile();
                newProfile1.setName("abcde");
                newProfile1.setMember(member);
                // 나머지 필드 값 설정
                newProfile1.setImg_url("/images/icons/user2.png");

                // profiles에 새로운 프로필 추가
                profiles.add(newProfile1);
// 더미 데이터
                // 필요한 정보를 모델에 추가한다
                model.addAttribute("memberId", memberId);
                model.addAttribute("profiles", profiles);
                int temp_index=member.getProfiles().size()+1;
                System.out.println("temp_index"+temp_index);

                model.addAttribute("temp_index", temp_index);
                System.out.println("memberId의 값은 "+memberId);
//                /images/icons/user1.png


            }

        }

////        MemberDto temp = (MemberDto) session.getAttribute("nowMember");
////        System.out.println("session result = " + temp);
//        if (temp == null) {
//            // 세션에 로그인 정보가 없을 경우 처리
//            System.out.println("템프가 null입니다 ");
//            int temp_index=1 ;
////            return "redirect:/login";
//            model.addAttribute("temp_index", temp_index);
//        }else{
////            int temp_index = (temp.getUsers() != null) ? temp.getUsers().size() : 1;
//        }
//
//        String email;
//        System.out.println("그리고 계속 ");
//        if(temp == null) {
//            email="";
//        }else{
////            email = (temp.getEmail() != null) ? temp.getEmail() : "";
//        }
//
//
//        if(email==""){
//            System.out.println("email은 null입니다 ");
//            System.out.println("email_result = " + email);
//        }else{
//            System.out.println("email_result = " + email);
//
//            List<String> userNames = (email != null) ? userService.getMatchingNames(email) : new ArrayList<>();
//
//            for (String userName : userNames) {
//                System.out.println("userName: " + userName);
//            }
//            int temp_index = (temp.getUsers() != null) ? temp.getUsers().size() : 1;
//            model.addAttribute("userNames", userNames);
//
//        }

        return "/profile";
    }

    @PostMapping("/profile")
    public String saveUserConfirm(@RequestParam("index") long index, Model model) {


        System.out.println(index);
//        // index 값을 세션에 저장
//        session.setAttribute("nowUserIndex", index);
//        System.out.println("Session nowUserIndex: " + session.getAttribute("nowUserIndex"));
//        System.out.println("index는 "+index);
//        User user=userRepository.findById(index);
//        session.setAttribute("nowUser", user);
//        System.out.println("now user: " + session.getAttribute("nowUser"));

        return "redirect:/";
    }


    @GetMapping("/profile-change")
    public String profileChangeForm(Model model,@RequestParam("index") long index) {



//        model.addAttribute("",);
        return "/profile-change";
    }

//    @PostMapping("/profile-change")
//    public String profileSubmit(@ModelAttribute("UserDto") @Valid UserDto userDto, BindingResult bindingResult) {
//        System.out.println("어떤 오류가 있는 걸까?");
//        if (bindingResult.hasErrors()) {
//            System.out.println("뭔가 오류가 있다 ");
//            return "redirect:/profile";
//        }
//        System.out.println(" 프로파일 등록 시작 ");
//        userService.registerUser(userDto);
//        System.out.println("잘 완성되었다 ");
//        return "redirect:/profile";
//    }

}
