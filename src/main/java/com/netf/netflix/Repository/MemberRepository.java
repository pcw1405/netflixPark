package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);

    Member findByNameAndPhoneNumber(String name, String phoneNumber);
}
