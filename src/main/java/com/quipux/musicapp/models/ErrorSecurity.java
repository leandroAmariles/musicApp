package com.quipux.musicapp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorSecurity {

    private String timestamp;

    private int status;

    private String error;

    private String path;
}
