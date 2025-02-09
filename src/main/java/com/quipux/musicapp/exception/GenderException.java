package com.quipux.musicapp.exception;


import lombok.Getter;

import java.util.List;

@Getter
public class GenderException extends RuntimeException {

    private List<String> allowedGenders;


    public GenderException(String message) {
        super(message);
    }


    public GenderException(String message, List<String> allowedGenders) {
        super(message);
        this.allowedGenders = allowedGenders;
    }


}
