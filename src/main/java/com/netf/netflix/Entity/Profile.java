package com.netf.netflix.Entity;

import com.netf.netflix.Dto.ProfileImgDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "maturity_level")
    private MaturityLevel maturityLevel;

    @Column(name = "imageUrl")
    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private ProfileImg profileImg;

    @ElementCollection
    @CollectionTable(name = "favorite_videos", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "video_id")
    private Set<Long> favoriteVideos;

    @Column(name = "recently_viewed_videos")
    @ElementCollection
    @CollectionTable(name = "recently_viewed_videos", joinColumns = @JoinColumn(name = "profile_id"))
    @OrderColumn(name = "viewed_videos_index")
    private List<Long> recentlyViewedVideos;

    public void setProfileImgDto(ProfileImgDto profileImgDto) {
        if (this.profileImg == null) {
            this.profileImg = new ProfileImg();
        }
        this.profileImg.setProfileImgName(profileImgDto.getProfileImgName());
        this.profileImg.setProfileOriImgName(profileImgDto.getProfileOriImgName());
        this.imageUrl = profileImgDto.getImageUrl();
    }

    public enum MaturityLevel {
        ALL,KID;

    }




}
