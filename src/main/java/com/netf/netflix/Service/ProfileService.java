package com.netf.netflix.Service;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }
}