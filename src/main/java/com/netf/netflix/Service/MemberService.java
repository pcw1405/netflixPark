package com.netf.netflix.Service;


import com.netf.netflix.Constant.Role;
import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor
public class MemberService  {
    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
//        this.passwordEncoder=passwordEncoder;
    }

    public Member findByEmail(String email){

        return memberRepository.findByEmail(email);
    }

    public MemberDto login(MemberDto memberDto) {
        Optional<Member> emailCheck = Optional.ofNullable(memberRepository.findByEmail(memberDto.getEmail()));
//        Optional<Member> emailConfirm= memberRepository.findByEmail(memberDto.getEmail());
        if (emailCheck.isPresent()) {
            Member member = emailCheck.get();
            if (member.getPassword().equals(memberDto.getPassword())) {
//                비밀번호
                MemberDto passwordConfirm = MemberDto.toMemberDto(member);
                return passwordConfirm;
            } else {
                return null;

            }
        } else {
            return null;
        }
    }
    @Transactional
        public void registerMember(MemberDto memberDto){
//        Member member =createMember(memberDto,passwordEncoder);
        Member member =createMember(memberDto);
        memberRepository.save(member);
        }
        private Member createMember(MemberDto memberDto){
        Member member =new Member();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
//        String password= passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(memberDto.getPassword());
        member.setRole(Role.ADMIN);
        return member;
    }


}
