package com.netf.netflix.Entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    private String email;
    private String name;
    // 다대일 관계 설정
    @ManyToOne
    @JoinColumn(name = "member_id") // "member_id"는 Member 엔티티에서 Member 엔티티의 ID와 매핑된 필드의 이름입니다.
    private Member member;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                // 주요 정보 및 필드 추가
                '}';
    }

}
