package com.example.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.board.model.error.ClientErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ClientErrorResponse> handleClientErrorException(ClientErrorException e){
        return new ResponseEntity<>(
            new ClientErrorResponse(e.getStatus(),e.getMessage()),e.getStatus()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClientErrorResponse> handleClientErrorException(MethodArgumentNotValidException e){

        var errorMessage = e.getFieldErrors().stream().map(fieldError -> (fieldError.getField() + ": "+fieldError.getDefaultMessage())).toList().toString();

        return new ResponseEntity<>(
            new ClientErrorResponse(HttpStatus.BAD_REQUEST,errorMessage),HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ClientErrorResponse> handleClientErrorException(HttpMessageNotReadableException e){
        return new ResponseEntity<>(
            new ClientErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()),HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ClientErrorResponse> handleClientErrorException(RuntimeException e){
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ClientErrorResponse> handleClientErrorException(Exception e){
       return ResponseEntity.internalServerError().build();
    }
}
