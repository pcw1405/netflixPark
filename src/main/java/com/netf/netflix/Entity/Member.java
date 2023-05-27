package com.netf.netflix.Entity;

import com.netf.netflix.constant.Role;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;




}
