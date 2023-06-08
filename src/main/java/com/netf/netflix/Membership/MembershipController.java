package com.netf.netflix.Membership;


import com.netf.netflix.Membership.MembershipRole;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Membership.MembershipService;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;



    @GetMapping(value = "/membership")
    public String membershipMain(){
        return "/membership/membershipSelect";
    }

    @PostMapping(value = "/membership/pay")
    public String membershipPay(@RequestParam("membershipRole")MembershipRole membershipRole,
                                Model model, HttpSession session){
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        LocalDate membershipExpirationDate = member.getMembershipExpirationDate();
        LocalDate currentDate = LocalDate.now();

        if (membershipExpirationDate != null) {
            LocalDate newExpirationDate = currentDate.plusMonths(1);
            member.setMembershipExpirationDate(newExpirationDate);
            model.addAttribute("message","맴버쉽이 연장되었습니다.");
            return "redirect:/membership";
        }else{
            LocalDate newExpirationDate = currentDate.plusMonths(1);
            membershipService.changeMembershipRole(member, membershipRole, newExpirationDate);
            model.addAttribute("message","맴버쉽이 결제되었습니다.");
            return "redirect:/membership";
        }
    }

}
