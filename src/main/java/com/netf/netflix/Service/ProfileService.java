package com.netf.netflix.Service;

import com.netf.netflix.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;
    public List<String> getMatchingNames(Long id) {

        return profileRepository.findMatchingNames(id);
    }

}
