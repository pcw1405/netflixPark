package com.netf.netflix.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="profile")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @Column(name="profile_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(unique = true)
    private String name;
//    private String email;

    private String img_url;

    @ManyToOne
    @JoinColumn(name = "member_id") // "member_id"는 Member 엔티티에서 Member 엔티티의 ID와 매핑된 필드의 이름입니다.
    private Member member;

}
