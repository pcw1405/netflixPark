package com.netf.netflix.Controller;

import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.User;
import com.netf.netflix.Repository.UserRepository;
import com.netf.netflix.Service.MemberService;
import com.netf.netflix.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginform(Model model){
        model.addAttribute("memberDto", new MemberDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginFormMember(@ModelAttribute MemberDto memberDto, HttpSession session){
        MemberDto loginConfirm = memberService.login(memberDto);



        if(loginConfirm != null){
            session.setAttribute("nowMember", loginConfirm);

            return "redirect:/register";
        } else{
            return "redirect:/login";
        }
    }


    @GetMapping("/profile")
    public String getUsers(HttpSession session, Model model){
        System.out.println("pro file start");
        MemberDto temp = (MemberDto) session.getAttribute("nowMember");
        System.out.println("session result="+temp);
        String email= temp.getEmail();
        System.out.println("email_result="+email);
        List<String> userNames = userService.getMatchingNames(email);

        for (String userName : userNames) {
            System.out.println("userName: " + userName);
        }

        model.addAttribute("userNames", userNames);

        return "/profile";
    }



    @PostMapping("/profile")
    public String saveProfile(@RequestParam("index") long index, HttpSession session) {
        User user = userRepository.findById(index);
        session.setAttribute("nowUser", user);

        return "redirect:/"; // 리다이렉트할 경로를 적절하게 수정해야 합니다.
    }




//    @GetMapping("/")
//    public String getProfile(Model model, HttpSession session) {
//        // 세션에서 nowUser 객체를 가져옴
//        User nowUser = (User) session.getAttribute("nowUser");
//        MemberDto nowMember = (MemberDto) session.getAttribute("nowMember");
//        // 모델에 nowUser 객체를 추가하여 뷰로 전달
//        model.addAttribute("nowUser", nowUser);
//        model.addAttribute("nowMember", nowMember);
//
//        String now_img=nowUser.getImg_url();
//        System.out.println("현재 유저의 프로파일 경로"+now_img);
//        System.out.println("현재 멤버목록= "+nowMember.getUsers());
//        return "home";
//
//    }




}
