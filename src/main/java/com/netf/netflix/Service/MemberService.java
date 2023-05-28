package com.netf.netflix.Service;


import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.MemberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    public MemberDto login(MemberDto memberDto) {
        Optional<Member> emailCheck= Optional.ofNullable(memberRepository.findByEmail(memberDto.getEmail()));
//        Optional<Member> emailConfirm= memberRepository.findByEmail(memberDto.getEmail());
        if(emailCheck.isPresent()){
            Member member = emailCheck.get();
            if(member.getPassword().equals(memberDto.getPassword())){
//                비밀번호
                MemberDto passwordConfirm= MemberDto.toMemberDto(member);
                return passwordConfirm;
            }else{
                return null;

            }
        }else{
            return null;
        }

    }



}
