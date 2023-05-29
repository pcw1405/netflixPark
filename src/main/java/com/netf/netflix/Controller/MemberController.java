package com.netf.netflix.Controller;

import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.User;
import com.netf.netflix.MemberRepository.UserRepository;
import com.netf.netflix.Service.MemberService;
import com.netf.netflix.Service.UserService;
import lombok.RequiredArgsConstructor;
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
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("member/login")
    public String loginform(){
        return "login";
    }

    @PostMapping("member/login")
    public String loginFormMember(@ModelAttribute MemberDto memberDto, HttpSession session){

            MemberDto loginConfirm = memberService.login(memberDto);
            System.out.println("result = "+loginConfirm);
            System.out.println("email =" +loginConfirm.getEmail());
//            String thisEmail = loginConfirm.getEmail();
            if(loginConfirm!=null){
                session.setAttribute("loginConfirm",loginConfirm);
                System.out.println("session result="+loginConfirm);
                return "redirect:/profile";
//                로그인 성공시 프로파일로
            } else{
                return "login";
//                로그인 실패시 다시 로그인화면으로
            }
    }


    @GetMapping("/profile")
    public String getUsers(HttpSession session, Model model){
        System.out.println("pro file start");
        MemberDto temp = (MemberDto) session.getAttribute("loginConfirm");
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
    public String saveUserConfirm(@RequestParam("index") long index, HttpSession session) {
//        // index 값을 세션에 저장
//        session.setAttribute("nowUserIndex", index);
//        System.out.println("Session nowUserIndex: " + session.getAttribute("nowUserIndex"));
        System.out.println("index는 "+index);
        User user=userRepository.findById(index);
        session.setAttribute("nowUser", user);
        System.out.println("now user: " + session.getAttribute("nowUser"));

        return "redirect:";
    }





}
