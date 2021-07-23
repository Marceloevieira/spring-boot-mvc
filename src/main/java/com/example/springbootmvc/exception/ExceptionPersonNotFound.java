package com.example.springbootmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionPersonNotFound extends Exception {


    public ExceptionPersonNotFound(Long id) {
        super(String.format("Person with id %s not found in the system.",id));
    }
}
