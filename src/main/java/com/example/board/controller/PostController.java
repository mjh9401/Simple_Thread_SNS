package com.example.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.Post;
import com.example.board.model.PostPatchRequestBody;
import com.example.board.model.PostPostRequestBody;
import com.example.board.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;
    
    @GetMapping
    public ResponseEntity<List<Post>> getPosts(){
        var posts = postService.getPosts();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable("postId") Long postId){
        Post machingPost = postService.getPostByPostId(postId);

        return ResponseEntity.ok(machingPost);
    }

    
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody){
        var post = postService.createPost(postPostRequestBody);

        return ResponseEntity.ok(post);     
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable("postId") Long postId, @RequestBody PostPatchRequestBody postPatchRequestBody){
        var post = postService.updatePost(postPatchRequestBody,postId);

        return ResponseEntity.ok(post); 
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);

        return ResponseEntity.noContent().build(); 
    }
}
