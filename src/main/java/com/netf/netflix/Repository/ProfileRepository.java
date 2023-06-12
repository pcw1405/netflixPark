package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByIdAndMemberId(Long id, String memberId);
    List<Profile> findByMember(Member member);
}
