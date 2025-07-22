package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.entity.UserEntity;
import com.example.board.model.post.Post;
import com.example.board.model.user.User;
import com.example.board.model.user.UserAuthenticationResponse;
import com.example.board.model.user.UserLoginRequestBody;
import com.example.board.model.user.UserPatchRequestBody;
import com.example.board.model.user.UserSignUpRequestBody;
import com.example.board.service.PostService;
import com.example.board.service.UserService;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false, name = "query") String query) {
        var users = userService.getUsers(query);

        return ResponseEntity.ok(users); 
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        var user = userService.getUser(username);

        return ResponseEntity.ok(user); 
    }
    
    @PatchMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody UserPatchRequestBody requestBody, Authentication authentication) {
        var user = userService.updateUser(username,requestBody, (UserEntity)authentication.getPrincipal());

        return ResponseEntity.ok(user); 
    }


    @GetMapping("/{username}/posts")
    public ResponseEntity<List<Post>> getPostByUsername(@PathVariable("username") String username) {
        var posts = postService.getPostByUsername(username);
       
        return ResponseEntity.ok(posts); 
    }

    @PostMapping("/{username}/follows")
    public ResponseEntity<User> follow(@PathVariable("username") String username, Authentication authentication) {
        var user = userService.follow(username, (UserEntity)authentication.getPrincipal());

        return ResponseEntity.ok(user); 
    }

    @DeleteMapping("/{username}/follows")
    public ResponseEntity<User> unfollow(@PathVariable("username") String username, Authentication authentication) {
        var user = userService.unfollow(username, (UserEntity)authentication.getPrincipal());

        return ResponseEntity.ok(user); 
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<User>> getFollowerByUser(@PathVariable("username") String username) {
        var followers = userService.getFollowersByUsername(username);

        return ResponseEntity.ok(followers); 
    }

    @GetMapping("/{username}/followings")
    public ResponseEntity<List<User>> getFollowingsByUser(@PathVariable("username") String username) {
        var followings = userService.getFollowingsByUsername(username);

        return ResponseEntity.ok(followings); 
    }

    @PostMapping
    public ResponseEntity<User> signUp(@Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody){
        var user = userService.signUp(
            userSignUpRequestBody.username(),
            userSignUpRequestBody.password()
        );

        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(@Valid @RequestBody UserLoginRequestBody userLoginRequestBody){
        var reponse = userService.authenticate(
            userLoginRequestBody.username(),
            userLoginRequestBody.password()
        );

        return ResponseEntity.ok(reponse);
    }

   
}
