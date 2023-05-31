package com.netf.netflix.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="profile_img")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImg {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;
    private String imgName; //이미지 파일명
    private String imgUrl; //이미지 조회 경로



}