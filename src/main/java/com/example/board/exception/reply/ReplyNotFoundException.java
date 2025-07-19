package com.example.board.exception.reply;

import org.springframework.http.HttpStatus;

import com.example.board.exception.ClientErrorException;

public class ReplyNotFoundException extends ClientErrorException {

    public ReplyNotFoundException(){
        super(HttpStatus.NOT_FOUND, "Reply not found");
    }

    public ReplyNotFoundException(Long replyId){
        super(HttpStatus.NOT_FOUND, "Reply not found with id: " + replyId);
    }  
    
    public ReplyNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
