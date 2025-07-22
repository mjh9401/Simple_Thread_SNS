package com.example.board.exception.follow;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;
import com.example.board.model.entity.UserEntity;

public class FollowAlreadyExistsException extends ClientErrorException {

    public FollowAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "Follow already exists");
    }

    public FollowAlreadyExistsException(UserEntity follower, UserEntity following){
        super(HttpStatus.CONFLICT, "Follow with follower " + follower.getUsername() +" and following " + following.getUsername() +" alreatdy exists");
    }  
    

}
