package com.netf.netflix.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CurstomPasswordEncoder implements PasswordEncoder {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public CurstomPasswordEncoder(){
        this.bCryptPasswordEncoder=new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword){
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword,String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
    }

}
