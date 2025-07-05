package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.UserSignUpRequestBody;
import com.example.board.model.user.User;
import com.example.board.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody UserSignUpRequestBody userSignUpRequestBody){
        var user = userService.signUp(
            userSignUpRequestBody.username(),
            userSignUpRequestBody.password()
        );

        return ResponseEntity.ok(user);
    }
    

}
