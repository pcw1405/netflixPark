package com.netf.netflix.MemberRepository;

import com.netf.netflix.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);

//    List<String> getMatchingUserNames(String email);


}
