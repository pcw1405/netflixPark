package com.netf.netflix.Constant;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum VideoRole {

    @Enumerated(EnumType.STRING)
    MOVIE, DRAMA
}