package com.netf.netflix.Dto;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {


    private long id;

    @NotBlank(message = "이름은 필수입력값입니다.")
    private String name;
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=8, max=16,message = "비밀번호는 8자이상 ,16자이하로 입력해주세요")
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
