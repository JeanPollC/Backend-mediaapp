package com.mitocode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelNotFoundExeption extends RuntimeException{

    public ModelNotFoundExeption(String message) {
        super(message);
    }
}
