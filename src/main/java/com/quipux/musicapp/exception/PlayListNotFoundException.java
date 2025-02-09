package com.quipux.musicapp.exception;

import lombok.Getter;

@Getter
public class PlayListNotFoundException extends RuntimeException {


    public PlayListNotFoundException(String message) {
        super(message);
    }
}
