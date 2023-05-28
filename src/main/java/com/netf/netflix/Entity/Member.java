package com.netf.netflix.Entity;

import com.netf.netflix.constant.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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

//    private String name;
//    네임은 멤버가 유저에만 있으면 될듯

    @Column(unique = true)
    private String email;

    private String password;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<User> users;


}
