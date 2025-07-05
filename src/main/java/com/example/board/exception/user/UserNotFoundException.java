package com.example.board.exception.user;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;

public class UserNotFoundException extends ClientErrorException {

    public UserNotFoundException(){
        super(HttpStatus.NOT_FOUND, "User not found");
    }

    public UserNotFoundException(String username){
        super(HttpStatus.NOT_FOUND, "User with username " + username +" not found");
    }  
    

}
