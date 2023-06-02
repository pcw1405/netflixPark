package com.netf.netflix.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private String name;
    private String language;
    private String nickname;
    private String maturityLevel;
    private String profileImageUrl;

    public ProfileDto() {
    }

}
