package com.netf.netflix.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="profiles")
@Getter
@Setter
@ToString
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "language")
    private String language;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "maturity_level")
    private String maturityLevel;
    //관람등급 구현?생각중
    @Nullable
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
