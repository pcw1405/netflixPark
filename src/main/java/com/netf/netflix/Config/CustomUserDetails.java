package com.netf.netflix.Config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public abstract class CustomUserDetails implements UserDetails {
    private String password; // 비밀번호 필드 추가

    private String email;
    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}