package com.enigma.challengedatapenduduk.Controller.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Data Is Not Found");
    }
    public NotFoundException(String message){
        super(message);
    }
}
