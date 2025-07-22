package com.example.board.exception.follow;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;

public class InvalidFollowException extends ClientErrorException {

    public InvalidFollowException(){
        super(HttpStatus.BAD_REQUEST, "Invalid follow request");
    } 
    
    public InvalidFollowException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
