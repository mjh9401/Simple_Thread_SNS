package com.example.board.exception.user;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;

public class UserAlreadyExistsException extends ClientErrorException {

    public UserAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "User already exists");
    }

    public UserAlreadyExistsException(String username){
        super(HttpStatus.CONFLICT, "User with username " + username +" alreatdy exists");
    }  
    

}
