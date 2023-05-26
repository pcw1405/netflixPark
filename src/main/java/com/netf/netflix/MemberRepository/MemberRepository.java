package com.netf.netflix.MemberRepository;

import com.netf.netflix.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);


}
