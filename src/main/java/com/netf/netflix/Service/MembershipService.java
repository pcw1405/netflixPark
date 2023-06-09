package com.netf.netflix.Service;

import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class MembershipService {
    public void changeMembershipRole(Member member, MembershipRole newRole, LocalDate membershipExpirationDate) {
        member.setMembershipRole(newRole);
        member.setMembershipExpirationDate(membershipExpirationDate);
    }
}
