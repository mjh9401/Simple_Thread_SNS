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
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.entity.UserEntity;
import com.example.board.model.reply.Reply;
import com.example.board.model.reply.ReplyPatchRequestBody;
import com.example.board.model.reply.ReplyPostRequestBody;
import com.example.board.service.ReplyService;

@RestController
@RequestMapping("/api/v1/posts/{postId}/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    
    @GetMapping
    public ResponseEntity<List<Reply>> getRepliesByPostId(@PathVariable("postId") Long postId){
        var replies = replyService.getRepliesByPostId(postId);

        return ResponseEntity.ok(replies);
    }

    
    @PostMapping
    public ResponseEntity<Reply> createReply(@PathVariable("postId") Long postId, @RequestBody ReplyPostRequestBody replyPostRequestBody, Authentication authentication){
        var reply = replyService.createReply(postId,replyPostRequestBody, (UserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(reply);     
    }

    @PatchMapping("/{replyId}")
    public ResponseEntity<Reply> updateReply(@PathVariable("postId") Long postId,@PathVariable("replyId") Long replyId,
                                             @RequestBody ReplyPatchRequestBody replyPatchRequestBody, Authentication authentication){
        var reply = replyService.updateReply(replyPatchRequestBody,postId,replyId,(UserEntity)authentication.getPrincipal());

        return ResponseEntity.ok(reply); 
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("postId") Long postId,@PathVariable("replyId") Long replyId, Authentication authentication){
        replyService.deleteReply(postId, replyId, (UserEntity) authentication.getPrincipal());

        return ResponseEntity.noContent().build(); 
    }
}
