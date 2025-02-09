package com.quipux.musicapp.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {



        @ExceptionHandler(GenderException.class)
        public ResponseEntity<ErrorResponse> handleGenderException(GenderException ex){
            log.error("GenderException: ", ex);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setDetails("Allowed genders: " + ex.getAllowedGenders());
            return ResponseEntity.badRequest().body(errorResponse);
        }

        @ExceptionHandler(GeneralException.class)
        public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex){
            log.error("GeneralException: ", ex);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(PlayListNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlayListNotFoundException(PlayListNotFoundException ex){
        log.error("PlayListNotFoundException: ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
