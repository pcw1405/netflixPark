package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface ProfileRepository extends JpaRepository<Profile,Long> {

//    @Query("SELECT p.member.name FROM Profile p WHERE p.id = :id")
//    String findMemberNameById(@Param("id") long id);

    @Query("SELECT p.name FROM Profile p WHERE p.member.id = :memberId")
    List<String> findMatchingNames(@Param("memberId") long memberId);

// 구하려는 것은 프로파일의 네임과 이미지
// 즉 기본적으로 박스의 인덱스가 필요하고
// 현재 맴버의 정보와 인덱스가 필요하고 그걸로 연산
// '멤버 정보'를 통해 유저정보를 가져오고 유저정보에서 '인덱스'를 가져온다
// 멤버정보는 멤버아이디 필드?로 부터 조인된다
// 

    Profile findById(long id);
}
