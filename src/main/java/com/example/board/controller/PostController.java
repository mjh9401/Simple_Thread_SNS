package com.example.board.controller;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.Post;

@RestController
public class PostController {
    
    @GetMapping("/api/v1/posts")
    public ResponseEntity<List<Post>> getPosts(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Post 1", ZonedDateTime.now()));
        posts.add(new Post(2L, "Post 2", ZonedDateTime.now()));
        posts.add(new Post(3L, "Post 3", ZonedDateTime.now()));

        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable("postId") Long postId){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Post 1", ZonedDateTime.now()));
        posts.add(new Post(2L, "Post 2", ZonedDateTime.now()));
        posts.add(new Post(3L, "Post 3", ZonedDateTime.now()));

        Optional<Post> machingPost = posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();

        return machingPost
            .map(post -> ResponseEntity.ok(post))
            .orElseGet(()-> ResponseEntity.notFound().build());
            
    }

    
}
