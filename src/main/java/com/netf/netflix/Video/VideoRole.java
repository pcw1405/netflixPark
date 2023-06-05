package com.netf.netflix.Video;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum VideoRole {

    @Enumerated(EnumType.STRING)
    MOVIE, DRAMA
}