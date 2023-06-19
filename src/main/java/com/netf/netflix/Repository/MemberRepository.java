package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByEmail(String email);

    Member findByNameAndPhoneNumber(String name, String phoneNumber);

    Member findByEmailAndPhoneNumber(String email, String PhoneNumber);

    @Query("SELECT v FROM Member v")
    Page<Member> findPaginated(Pageable pageable);
}
