package com.netf.netflix.Entity;


import com.netf.netflix.Dto.MemberDto;
import com.netf.netflix.Constant.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Profile> profiles;

    @Enumerated(EnumType.STRING)
    private Role role;




}
