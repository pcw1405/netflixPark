package com.netf.netflix.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="profile_img")
@Getter
@Setter
public class ProfileImg {
    @Id
    @Column(name="profile_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String profileImgName;

    @Column
    private String ProfileOriImgName;

    @Column(name="imageUrl")
    private String imageUrl;

    @ManyToOne(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;


    public void createProImg(String profileImgName,String profileOriImgName,String profileImgUrl){
        this.profileImgName=profileImgName;
        this.ProfileOriImgName=profileImgName;
        this.imageUrl=profileImgUrl;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public void setProfileImgUrl(String profileImgUrl) {
        this.imageUrl = profileImgUrl;
    }

    public String getUrl() {
        return imageUrl;
    }
}
