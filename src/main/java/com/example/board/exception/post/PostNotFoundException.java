package com.example.board.exception.post;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;

public class PostNotFoundException extends ClientErrorException {

    public PostNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Post not found");
    }

    public PostNotFoundException(Long postId){
        super(HttpStatus.NOT_FOUND, "Post not found with id: " + postId);
    }  
    
    public PostNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
