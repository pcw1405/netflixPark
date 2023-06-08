//package com.netf.netflix.Entity;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@Table(name="profile_img")
//public class ProfileImg {
//    @Id
//    @Column(name = "profile_img_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String profileImgName;
//
//    private String profileOriImgName;
//
//    @Column(name = "profile_image_url")
//    private String profileImgUrl;
//
//    @OneToOne(mappedBy = "profileImg")
//    private Profile profile;
//
//    public ProfileImg() {
//
//    }
//
//    public ProfileImg(String profileOriImgName, String profileImgName, String profileImgUrl) {
//        this.profileOriImgName = profileOriImgName;
//        this.profileImgName = profileImgName;
//        this.profileImgUrl = profileImgUrl;
//    }
//
//    public String getProfileImageUrl() {
//        return profileImgUrl;
//    }
//}