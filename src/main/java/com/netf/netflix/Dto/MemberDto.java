package com.netf.netflix.Dto;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.User;
import com.netf.netflix.constant.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    private List<User> users;

    public static MemberDto toMemberDto(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setUsers(member.getUsers());
        return memberDto;
    }



}
