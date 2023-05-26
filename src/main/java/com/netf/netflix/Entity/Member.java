package com.netf.netflix.Entity;

import lombok.*;

import javax.management.relation.Role;
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
