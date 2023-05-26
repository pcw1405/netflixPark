package com.netf.netflix.Service;


import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.MemberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
//@RequiredArgsConstructor
public class MemberService  {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    public Member findByEmail(String email){

        return memberRepository.findByEmail(email);
    }




}
