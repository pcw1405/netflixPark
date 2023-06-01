package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);

    Optional<Member> findById(long id);

//    List<String> getMatchingUserNames(String email);


}
