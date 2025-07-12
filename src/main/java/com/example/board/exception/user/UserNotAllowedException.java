package com.example.board.exception.user;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;

public class UserNotAllowedException extends ClientErrorException {

    public UserNotAllowedException(){
        super(HttpStatus.FORBIDDEN, "User now allowed");
    }
    
}
