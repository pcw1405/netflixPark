package com.netf.netflix.Service;


import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Constant.Role;
import com.netf.netflix.Entity.Member;

import com.netf.netflix.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Member findMember= memberRepository.findByEmail(member.getEmail());
        if(findMember !=null){
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Member member=memberRepository.findByEmail(email);

        if(member==null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(Member member, String newPassword) {
        member.setPassword(newPassword);
    }

    public void changePhoneNumber(Member member, String phoneNum) {
        member.setPhoneNumber(phoneNum);
    }
    public void membershipDrop(Member member) {
        member.setMembershipRole(MembershipRole.NONE);
    }


}

