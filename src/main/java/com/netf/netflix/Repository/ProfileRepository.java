package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository <Profile, Long>{
}
