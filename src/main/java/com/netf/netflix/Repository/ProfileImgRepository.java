package com.netf.netflix.Repository;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.ProfileImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImgRepository extends JpaRepository<ProfileImg,Long> {
    ProfileImg findByProfile(Profile profile);
}
