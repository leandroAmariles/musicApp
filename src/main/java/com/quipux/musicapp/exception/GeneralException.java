package com.quipux.musicapp.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    public GeneralException(String message) {
        super(message);
    }
}
