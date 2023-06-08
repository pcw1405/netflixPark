package com.netf.netflix.Membership;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberShipController {

    @GetMapping(value = "/membership")
    public String membershipSelect(){
        return "membership/membershipSelect";
    }
}
