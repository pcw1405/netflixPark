package com.netf.netflix.Service;


import com.netf.netflix.MemberRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<String> getMatchingNames(String email) {
        return userRepository.findMatchingNames(email);
    }
}
