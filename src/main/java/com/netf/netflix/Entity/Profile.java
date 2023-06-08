package com.netf.netflix.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
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

    @Column(name="image_path")
    private String imagePath = "/images/icons/plus.png";

//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "profile_img_id")
//    private ProfileImg profileImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Getters and setters

//    public void setProfileImg(ProfileImg profileImg) {
//        this.profileImg = profileImg;
//        profileImg.setProfile(this);
//    }
//
//    public ProfileImg getProfileImg() {
//        return profileImg;
//    }
}
