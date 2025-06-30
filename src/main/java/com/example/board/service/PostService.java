package com.example.board.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.board.model.Post;
import com.example.board.model.PostPatchRequestBody;
import com.example.board.model.PostPostRequestBody;
import com.example.board.repository.PostEntityRepository;

@Service
public class PostService {

    @Autowired
    private PostEntityRepository postEntityRepository;


    public List<Post> getPosts(){
        var postEntities = postEntityRepository.findAll();
        
        return postEntities.stream().map(Post::from).toList();
    }

    public Post getPostByPostId(Long postId){
        var postEntity = postEntityRepository.findById(postId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found."));

        return Post.from(postEntity);
    }

    public Post createPost(PostPostRequestBody postPostRequestBody) {
        var postId = posts.stream().mapToLong(Post::getPostId).max().orElse(0L)+1;

        var newPost = new Post(postId, postPostRequestBody.body(), ZonedDateTime.now());
        posts.add(newPost);

        return newPost;
    }

    public Post updatePost(PostPatchRequestBody postPatchRequestBody, Long postId) {
       Optional<Post> postOptional = posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();

       if(postOptional.isPresent()){
        Post postToUpdate = postOptional.get();
        postToUpdate.setBody(postPatchRequestBody.body());

        return postToUpdate; 
       }else{
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found.");
       }
    }

    public void deletePost(Long postId) {
        Optional<Post> postOptional = posts.stream().filter(post -> postId.equals(post.getPostId())).findFirst();

       if(postOptional.isPresent()){
        posts.remove(postOptional.get());
       }else{
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found.");
       }
    }   


    
}
