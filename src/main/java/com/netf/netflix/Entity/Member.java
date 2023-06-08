package com.netf.netflix.Entity;

import com.netf.netflix.Membership.MembershipRole;
import com.netf.netflix.Constant.Role;
import com.netf.netflix.Dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "이메일은 필수입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private MembershipRole membershipRole;

    private LocalDate membershipExpirationDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Profile> profiles = new ArrayList<>();

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        member.setMembershipRole(MembershipRole.NONE);
        return member;
    }

    public Profile createProfile(String name, String language, String nickname, String maturityLevel) {
        if (profiles.size() >= 5) {
            throw new RuntimeException("프로필은 최대 5개까지 생성할 수 있습니다.");
        }

        Profile profile = new Profile();
        profile.setName(name);
        profile.setLanguage(language);
        profile.setNickname(nickname);
        profile.setMaturityLevel(maturityLevel);
        profile.setMember(this);

        profiles.add(profile);

        return profile;
    }
    // 생성자, 게터, 세터, toString 등의 생략된 코드
}
