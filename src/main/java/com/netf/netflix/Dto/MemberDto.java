package com.netf.netflix.Dto;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.constant.Role;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {

    private long id;
    private String name;
    private String email;
    private String password;
//    private Role role;
    public static MemberDto toMemberDto(Member member){
        MemberDto memberDto =new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        return memberDto;
    }

}
