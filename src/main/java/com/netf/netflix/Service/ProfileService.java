package com.netf.netflix.Service;

import com.netf.netflix.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository){
        this.profileRepository= profileRepository;
    }
}
